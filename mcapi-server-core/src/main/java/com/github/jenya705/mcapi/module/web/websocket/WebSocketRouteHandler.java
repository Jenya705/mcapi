package com.github.jenya705.mcapi.module.web.websocket;

/**
 * @author Jenya705
 */
public interface WebSocketRouteHandler {

    void connection(WebSocketConnection connection);

    void message(WebSocketConnection connection, WebSocketMessage message);

    void close(WebSocketConnection connection);
}
