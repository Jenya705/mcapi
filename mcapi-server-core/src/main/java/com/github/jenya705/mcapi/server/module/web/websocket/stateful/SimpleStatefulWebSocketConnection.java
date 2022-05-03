package com.github.jenya705.mcapi.server.module.web.websocket.stateful;

import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketMessage;
import com.github.jenya705.mcapi.server.module.web.websocket.container.SimpleWebSocketContainerConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class SimpleStatefulWebSocketConnection<S> extends SimpleWebSocketContainerConnection implements StatefulWebSocketConnection<S> {

    private final Map<S, Function<WebSocketMessage, Object>> stateFunctions = new HashMap<>();

    private final AtomicReference<S> currentState = new AtomicReference<>();

    @Override
    public Object onMessage(WebSocketMessage message) {
        return stateFunctions.get(currentState.get()).apply(message);
    }

    @Override
    public S getState() {
        return this.currentState.get();
    }

    @Override
    public void changeState(S newState) {
        this.currentState.set(newState);
    }

    @Override
    public StatefulWebSocketConnection<S> defaultState(S state) {
        if (this.currentState.get() == null) this.currentState.set(state);
        return this;
    }

    @Override
    public StatefulWebSocketConnection<S> state(S state, Function<WebSocketMessage, Object> stateFunction) {
        stateFunctions.put(state, stateFunction);
        return this;
    }
}
