package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.Route;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketRouteHandler;

/**
 * @author Jenya705
 */
public interface WebServer {

    void addHandler(RoutePredicate requestPredicate, RouteHandler handler, boolean readBody);

    void addWebSocketHandler(String uri, WebSocketRouteHandler handler);

    default void addHandler(Route route, RouteHandler handler) {
        addHandler(new DefaultRoutePredicate(route), handler, route.getMethod() != HttpMethod.GET);
    }

    default void addWebSocketHandler(Route route, WebSocketRouteHandler handler) {
        addWebSocketHandler(route.getUri(), handler);
    }

    default WebServer handler(RoutePredicate requestPredicate, RouteHandler handler, boolean readBody) {
        addHandler(requestPredicate, handler, readBody);
        return this;
    }

    default WebServer webSocket(String uri, WebSocketRouteHandler handler) {
        addWebSocketHandler(uri, handler);
        return this;
    }

    default WebServer webSocket(Route route, WebSocketRouteHandler handler) {
        addWebSocketHandler(route, handler);
        return this;
    }

    default WebServer handler(Route route, RouteHandler handler) {
        addHandler(route, handler);
        return this;
    }
}
