package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.entity.api.EntityError;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.util.Pair;
import com.github.jenya705.mcapi.util.ReactiveUtils;
import com.github.jenya705.mcapi.util.ReactorUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpPredicateUtils;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jenya705
 */
@Slf4j
public class ReactorServer implements WebServer, BaseCommon {

    private final List<Pair<Route, RouteHandler>> routeImplementations = new ArrayList<>();

    @Bean
    @Getter
    private Mapper mapper;

    private HttpServer server;
    private DisposableServer nettyServer;

    @OnStartup(priority = 4)
    public void start() {
        log.info("Starting web server...");
        server = HttpServer
                .create()
                .port(8081)
                .route(routes ->
                        routeImplementations.forEach(routeImplementation ->
                                route(routeImplementation, routes)
                        )
                );
        nettyServer = server
                .bind()
                .doOnError(ReactiveUtils::runtimeException)
                .block();
        log.info("Done! (Starting web server...)");
    }

    @Override
    public void addHandler(Route route, RouteHandler handler) {
        Pair<Route, RouteHandler> routeImplementation = new Pair<>(route, handler);
        routeImplementations.add(routeImplementation);
        if (server != null) {
            server.route(routes ->
                    route(routeImplementation, routes)
            );
        }
    }

    private void route(Pair<Route, RouteHandler> routeImplementation, HttpServerRoutes routes) {
        routes
                .route(
                        HttpPredicateUtils.predicate(routeImplementation.getLeft()),
                        (request, response) -> {
                            ReactorRequest localRequest = new ReactorRequest(this, request);
                            ReactorResponse localResponse = new ReactorResponse(response);
                            try {
                                routeImplementation.getRight().handle(localRequest, localResponse);
                            } catch (Throwable e) {
                                e.printStackTrace();
                                ApiError error;
                                if (e instanceof Exception) {
                                    error = ApiError.raw((Exception) e);
                                }
                                else {
                                    error = new EntityError(
                                            500,
                                            0,
                                            null,
                                            "Bad"
                                    );
                                }
                                localResponse
                                        .status(error.getStatusCode())
                                        .body(error);
                            }
                            return response
                                    .sendString(ReactorUtils.mono(() ->
                                            mapper.asJson(localResponse.getBody())
                                    ));
                        }
                );
    }
}
