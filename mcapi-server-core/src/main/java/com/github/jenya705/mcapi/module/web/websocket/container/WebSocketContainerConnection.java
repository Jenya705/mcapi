package com.github.jenya705.mcapi.module.web.websocket.container;

import com.github.jenya705.mcapi.module.web.websocket.WebSocketConnection;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketMessage;

/**
 * @author Jenya705
 */
public interface WebSocketContainerConnection extends WebSocketConnection {

    Object onMessage(WebSocketMessage message);

    default void onConnection() {
    }

    default void onError(Throwable e) {
    }

    default void onClose() {
    }

    void delegate(WebSocketConnection connection);
}
