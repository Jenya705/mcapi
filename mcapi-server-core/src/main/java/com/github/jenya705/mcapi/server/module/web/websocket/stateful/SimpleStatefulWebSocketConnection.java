package com.github.jenya705.mcapi.server.module.web.websocket.stateful;

import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketMessage;
import com.github.jenya705.mcapi.server.module.web.websocket.container.SimpleWebSocketContainerConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class SimpleStatefulWebSocketConnection<S> extends SimpleWebSocketContainerConnection implements StatefulWebSocketConnection<S> {

    private final Map<S, Function<WebSocketMessage, Object>> stateFunctions = new HashMap<>();

    private S currentState;

    @Override
    public Object onMessage(WebSocketMessage message) {
        return stateFunctions.get(currentState).apply(message);
    }

    @Override
    public S getState() {
        return this.currentState;
    }

    @Override
    public void changeState(S newState) {
        this.currentState = newState;
    }

    @Override
    public StatefulWebSocketConnection<S> defaultState(S state) {
        if (this.currentState == null) this.currentState = state;
        return this;
    }

    @Override
    public StatefulWebSocketConnection<S> state(S state, Function<WebSocketMessage, Object> stateFunction) {
        stateFunctions.put(state, stateFunction);
        return this;
    }
}
