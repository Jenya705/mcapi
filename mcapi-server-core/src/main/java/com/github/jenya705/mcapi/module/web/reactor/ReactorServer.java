package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.log.TimerTask;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.module.web.*;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketRouteHandler;
import com.github.jenya705.mcapi.util.Pair;
import com.github.jenya705.mcapi.util.ReactiveUtils;
import com.github.jenya705.mcapi.util.ReactorUtils;
import com.github.jenya705.mcapi.util.ZipUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.*;
import reactor.netty.http.websocket.WebsocketInbound;
import reactor.netty.http.websocket.WebsocketOutbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
public class ReactorServer extends AbstractApplicationModule implements WebServer {

    private final List<ReactorRouteImplementation> routeImplementations = new ArrayList<>();
    private final List<Pair<String, WebSocketRouteHandler>> webSocketRouteImplementations = new ArrayList<>();

    @Bean
    private Mapper mapper;

    private WebConfig config;

    private HttpServer server;
    private DisposableServer nettyServer;

    @OnStartup(priority = 4)
    public void start() {
        config = new WebConfig(
                bean(ConfigModule.class)
                        .getConfig()
                        .required("web")
        );
        TimerTask timerTask = TimerTask.start(log, "Starting web server...");
        server = HttpServer
                .create()
                .port(config.getPort())
                .route(routes -> {
                    routeImplementations.forEach(routeImplementation ->
                            route(routeImplementation, routes)
                    );
                    webSocketRouteImplementations.forEach(webSocketRouteImplementation ->
                            webSocketRoute(webSocketRouteImplementation, routes)
                    );
                });
        nettyServer = server
                .bind()
                .doOnError(ReactiveUtils::runtimeException)
                .block();
        timerTask.complete();
        log.info("Web server available on port {}", config.getPort());
    }

    @Override
    public void addHandler(RoutePredicate routePredicate, RouteHandler handler, boolean readBody) {
        ReactorRouteImplementation routeImplementation = new ReactorRouteImplementation(routePredicate, handler, readBody);
        routeImplementations.add(routeImplementation);
        if (server != null) {
            server.route(routes ->
                    route(routeImplementation, routes)
            );
        }
    }

    @Override
    public void addWebSocketHandler(String uri, WebSocketRouteHandler handler) {
        Pair<String, WebSocketRouteHandler> webSocketRouteImplementation = new Pair<>(uri, handler);
        webSocketRouteImplementations.add(webSocketRouteImplementation);
        if (server != null) {
            server.route(routes ->
                    webSocketRoute(webSocketRouteImplementation, routes)
            );
        }
    }

    private void route(ReactorRouteImplementation routeImplementation, HttpServerRoutes routes) {
        routes
                .route(
                        new ReactorRoutePredicate(routeImplementation.getRoutePredicate()),
                        routeImplementation.isReadBody() ?
                                routeWithBody(
                                        routeImplementation.getRouteHandler(),
                                        routeParameters(routeImplementation.getRoutePredicate())
                                ) :
                                routeWithoutBody(
                                        routeImplementation.getRouteHandler(),
                                        routeParameters(routeImplementation.getRoutePredicate())
                                )
                );
    }

    private BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> routeWithBody(RouteHandler handler, RouteParameters parameters) {
        return (request, response) ->
                response.sendString(
                        request
                                .receive()
                                .aggregate()
                                .asString()
                                .map(body -> executeHandler(request, response, handler, body, parameters))
                );
    }

    private BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> routeWithoutBody(RouteHandler handler, RouteParameters parameters) {
        return (request, response) ->
                response.sendString(
                        ReactorUtils.mono(() ->
                                executeHandler(request, response, handler, null, parameters)
                        )
                );
    }

    private String executeHandler(HttpServerRequest request, HttpServerResponse response, RouteHandler handler, String body, RouteParameters parameters) {
        ReactorRequest localRequest = new ReactorRequest(this, request, body, parameters);
        ReactorResponse localResponse = new ReactorResponse(response);
        localResponse.contentType("application/json");
        try {
            handler.handle(localRequest, localResponse);
        } catch (Throwable e) {
            if (app().isDebug()) {
                log.info("Exception on request: ", e);
            }
            ApiError error = mapper.asApiError(e);
            localResponse
                    .status(error.getStatusCode())
                    .body(error);
        }
        return mapper.asJson(localResponse.getBody());
    }

    private void webSocketRoute(Pair<String, WebSocketRouteHandler> webSocketRouteImplementation, HttpServerRoutes routes) {
        routes
                .ws(
                        webSocketRouteImplementation.getLeft(),
                        (in, out) -> executeWebSocketHandler(
                                in, out,
                                webSocketRouteImplementation.getRight()
                        )
                );
    }

    private Publisher<Void> executeWebSocketHandler(WebsocketInbound inbound, WebsocketOutbound outbound, WebSocketRouteHandler handler) {
        ReactorWebSocketConnection connection = new ReactorWebSocketConnection(inbound, outbound, this);
        handler.onConnect(connection);
        inbound
                .receiveCloseStatus()
                .subscribe(closeStatus -> {
                    handler.onClose(connection);
                    if (connection.getSink() != null) {
                        connection.getSink().complete();
                    }
                });
        return outbound.sendByteArray(
                Flux
                        .<String>create(sink -> {
                            connection.setSink(sink);
                            inbound
                                    .receive()
                                    .asByteArray()
                                    .map(ZipUtils::decompressSneaky)
                                    .map(String::new)
                                    .map(message ->
                                            Objects.requireNonNullElse(
                                                    handler.onMessage(
                                                            connection,
                                                            new ReactorWebSocketMessage(message, this)
                                                    ),
                                                    ""
                                            )
                                    )
                                    .doOnError(e -> handler.onError(connection, e))
                                    .onErrorResume(e -> Mono.just(mapper.asApiError(e)))
                                    .map(mapper::asJson)
                                    .filter(String::isEmpty)
                                    .subscribe(sink::next);
                        })
                        .map(String::getBytes)
                        .map(ZipUtils::compressSneaky)
                        .doOnError(e -> handler.onError(connection, e))
                        .doOnError(e -> outbound.sendClose())
        );
    }

    private RouteParameters routeParameters(Object obj) {
        return obj instanceof RouteParameters ? (RouteParameters) obj : null;
    }

}
