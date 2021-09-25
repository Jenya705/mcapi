package com.github.jenya705.mcapi.module.web.websocket.container;

import com.github.jenya705.mcapi.module.web.websocket.WebSocketConnection;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public abstract class SimpleWebSocketContainerConnection implements WebSocketContainerConnection {

    @Delegate
    private WebSocketConnection connection;

    @Override
    public void delegate(WebSocketConnection connection) {
        this.connection = connection;
    }
}
