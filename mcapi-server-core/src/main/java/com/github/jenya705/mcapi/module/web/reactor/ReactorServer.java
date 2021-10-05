package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.log.TimerTask;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;
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
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
public class ReactorServer extends AbstractApplicationModule implements WebServer {

    private final List<Pair<Route, RouteHandler>> routeImplementations = new ArrayList<>();
    private final List<Pair<String, WebSocketRouteHandler>> webSocketRouteImplementations = new ArrayList<>();

    @Bean
    private Mapper mapper;

    private HttpServer server;
    private DisposableServer nettyServer;

    @OnStartup(priority = 4)
    public void start() {
        TimerTask timerTask = TimerTask.start(log, "Starting web server...");
        server = HttpServer
                .create()
                .port(8081)
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
        localResponse.contentType("application/json");
        try {
            handler.handle(localRequest, localResponse);
        } catch (Throwable e) {
            e.printStackTrace();
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
}
