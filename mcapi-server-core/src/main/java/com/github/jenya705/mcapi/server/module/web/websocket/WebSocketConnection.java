package com.github.jenya705.mcapi.server.module.web.websocket;

import java.util.Optional;

/**
 * @author Jenya705
 */
public interface WebSocketConnection {

    default Optional<String> header(String key) {
        return header(key, String.class);
    }

    <T> Optional<T> header(String key, Class<? extends T> clazz);

    void close();

    void send(Object obj);
}
