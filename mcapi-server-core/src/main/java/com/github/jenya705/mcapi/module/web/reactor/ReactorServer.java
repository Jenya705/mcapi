package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.entity.api.EntityError;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.util.Pair;
import com.github.jenya705.mcapi.util.ReactiveUtils;
import com.github.jenya705.mcapi.util.ReactorUtils;
import com.github.jenya705.mcapi.util.ZipUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Jenya705
 */
@Slf4j
public class ReactorServer extends AbstractApplicationModule implements WebServer {

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
                .route(routes -> {
                    routeImplementations.forEach(routeImplementation ->
                            route(routeImplementation, routes)
                    );
//                    routes
//                            .ws("/ws", (websocketInbound, websocketOutbound) -> {
//                                System.out.println(websocketInbound.toString());
//                                return websocketOutbound
//                                        .sendByteArray(
//                                                websocketInbound
//                                                        .receive()
//                                                        .retain()
//                                                        .asByteArray()
//                                                        .map(ZipUtils::decompressSneaky)
//                                                        .map(String::new)
//                                                        .map(str -> {
//                                                            System.out.println(str);
//                                                            return str;
//                                                        })
//                                                        .map(str -> ZipUtils.compressSneaky(str.getBytes()))
//                                                        .doOnError(e -> e.printStackTrace())
//                                        );
//                            });
                });
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
                        routeImplementation.getLeft().getMethod().isWithBody() ?
                                routeWithBody(routeImplementation.getRight()) :
                                routeWithoutBody(routeImplementation.getRight())
                );
    }

    private BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> routeWithBody(RouteHandler handler) {
        return (request, response) ->
                response.sendString(
                        request
                                .receive()
                                .aggregate()
                                .asString()
                                .map(body -> executeHandler(request, response, handler, body))
                );
    }

    private BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> routeWithoutBody(RouteHandler handler) {
        return (request, response) ->
                response.sendString(
                        ReactorUtils.mono(() ->
                                executeHandler(request, response, handler, null)
                        )
                );
    }

    private String executeHandler(HttpServerRequest request, HttpServerResponse response, RouteHandler handler, String body) {
        ReactorRequest localRequest = new ReactorRequest(this, request, body);
        ReactorResponse localResponse = new ReactorResponse(response);
        try {
            handler.handle(localRequest, localResponse);
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
        return mapper.asJson(localResponse.getBody());
    }
}
