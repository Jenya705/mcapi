package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.Route;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketRouteHandler;

/**
 * @author Jenya705
 */
public interface WebServer {

    void addHandler(Route route, RouteHandler handler);

    void addWebSocketHandler(String uri, WebSocketRouteHandler handler);

    default void addWebSocketHandler(Route route, WebSocketRouteHandler handler) {
        addWebSocketHandler(route.getUri(), handler);
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
