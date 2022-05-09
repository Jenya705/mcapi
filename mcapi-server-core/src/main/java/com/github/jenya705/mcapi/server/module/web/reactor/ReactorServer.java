package com.github.jenya705.mcapi.server.module.web.reactor;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.module.web.*;
import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketRouteHandler;
import com.github.jenya705.mcapi.server.util.Pair;
import com.github.jenya705.mcapi.server.util.StringUtils;
import com.github.jenya705.mcapi.utils.ZipUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import reactor.netty.http.server.HttpServerRoutes;
import reactor.netty.http.websocket.WebsocketInbound;
import reactor.netty.http.websocket.WebsocketOutbound;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;

/**
 * @author Jenya705
 */
@Getter
@Singleton
@Setter(AccessLevel.PRIVATE)
public class ReactorServer extends AbstractApplicationModule implements WebServer {

    private static final String jsonContentType = "application/json";

    private final List<ReactorRouteImplementation> routeImplementations = new CopyOnWriteArrayList<>();
    private final List<Pair<String, WebSocketRouteHandler>> webSocketRouteImplementations = new CopyOnWriteArrayList<>();

    private final Mapper mapper;
    private final WebConfig config;
    private final Logger log;

    private HttpServer server;
    private DisposableServer nettyServer;

    @Inject
    public ReactorServer(ServerApplication application, Mapper mapper, ConfigModule configModule, Logger log) {
        super(application);
        this.mapper = mapper;
        this.log = log;
        config = new WebConfig(
                configModule
                        .getConfig()
                        .required("web")
        );
    }

    @OnStartup(priority = 4)
    public void start() {
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
        server.bind()
                .doOnError(e -> {
                    log.error("Failed to start server:", e);
                    app().stop();
                })
                .subscribe(this::setNettyServer);
        timerTask.complete();
        log.info("Web server will be available soon on port {}", config.getPort());
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
                                .flatMap(body -> executeHandler(request, response, handler, body, parameters))
                );
    }

    private BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> routeWithoutBody(RouteHandler handler, RouteParameters parameters) {
        return (request, response) ->
                response.sendString(
                        executeHandler(request, response, handler, null, parameters)
                );
    }

    private Mono<String> executeHandler(HttpServerRequest request, HttpServerResponse response, RouteHandler handler, String body, RouteParameters parameters) {
        if (debug()) {
            log.info("Received request to uri {} with body {}", request.uri(), body);
        }
        ReactorRequest localRequest = new ReactorRequest(this, request, body, parameters);
        return Mono.defer(() -> handler.handle(localRequest))
                .onErrorResume(err -> Mono.just(
                        Response.create().error(err)
                ))
                .map(handlerResponse -> {
                    if(!handlerResponse.getHeaders().containsKey("content-type")) {
                        handlerResponse.header("content-type", jsonContentType);
                    }
                    handlerResponse.getHeaders().forEach(response::header);
                    response.status(handlerResponse.getStatus());
                    if (handlerResponse.getHeaders().get("content-type").equals(jsonContentType)) {
                        return mapper.asJson(handlerResponse.getBody());
                    }
                    return String.valueOf(handlerResponse.getBody());
                });
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
                                    .doOnNext(message -> debug(
                                            () -> log.info("Received message from webSocket. message: {}", message)
                                    ))
                                    .map(message ->
                                            Objects.requireNonNullElse(
                                                    handler.onMessage(
                                                            connection,
                                                            new ReactorWebSocketMessage(message, this)
                                                    ),
                                                    ""
                                            )
                                    )
                                    .doOnError(e -> debug(
                                            () -> log.warn("Received error while trying to read webSocket message", e)
                                    ))
                                    .doOnError(e -> handler.onError(connection, e))
                                    .onErrorResume(e -> Mono.just(mapper.asApiError(e)))
                                    .map(mapper::asJson)
                                    .filter(it -> !it.isEmpty())
                                    .subscribe(sink::next);
                        })
                        .map(StringUtils::getStandardBytes)
                        .map(ZipUtils::compressSneaky)
                        .doOnError(e -> handler.onError(connection, e))
                        .doOnError(e -> outbound.sendClose())
        );
    }

    private RouteParameters routeParameters(Object obj) {
        return obj instanceof RouteParameters ? (RouteParameters) obj : null;
    }
}
