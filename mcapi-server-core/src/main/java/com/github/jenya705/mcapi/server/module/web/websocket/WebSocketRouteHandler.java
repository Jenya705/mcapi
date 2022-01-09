package com.github.jenya705.mcapi.server.module.web.websocket;

/**
 * @author Jenya705
 */
public interface WebSocketRouteHandler {

    Object onMessage(WebSocketConnection connection, WebSocketMessage message);

    default void onConnect(WebSocketConnection connection) {
    }

    default void onClose(WebSocketConnection connection) {
    }

    default void onError(WebSocketConnection connection, Throwable throwable) {
    }
}
