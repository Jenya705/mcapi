package com.github.jenya705.mcapi.server.module.web.websocket.stateful;

import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketMessage;
import com.github.jenya705.mcapi.server.module.web.websocket.container.WebSocketContainerConnection;

import java.util.function.Function;

/**
 * @author Jenya705
 */
public interface StatefulWebSocketConnection<S> extends WebSocketContainerConnection {

    static <S> StatefulWebSocketConnection<S> simple(S defaultState) {
        return new SimpleStatefulWebSocketConnection<S>()
                .defaultState(defaultState);
    }

    S getState();

    void changeState(S newState);

    StatefulWebSocketConnection<S> defaultState(S state);

    StatefulWebSocketConnection<S> state(S state, Function<WebSocketMessage, Object> stateFunction);
}
