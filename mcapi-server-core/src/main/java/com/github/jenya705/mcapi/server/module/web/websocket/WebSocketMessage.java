package com.github.jenya705.mcapi.server.module.web.websocket;

/**
 * @author Jenya705
 */
public interface WebSocketMessage {

    default String get() {
        return as(String.class);
    }

    <T> T as(Class<? extends T> clazz);
}
