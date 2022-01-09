package com.github.jenya705.mcapi.server.module.web.reactor;

import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketMessage;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class ReactorWebSocketMessage implements WebSocketMessage {

    private final String body;
    private final ReactorServer server;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T as(Class<? extends T> clazz) {
        if (clazz == String.class) return (T) body;
        return server.getMapper().fromJson(body, clazz);
    }
}
