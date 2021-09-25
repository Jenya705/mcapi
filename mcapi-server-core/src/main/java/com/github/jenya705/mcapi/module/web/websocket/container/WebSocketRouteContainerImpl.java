package com.github.jenya705.mcapi.module.web.websocket.container;

import com.github.jenya705.mcapi.module.web.websocket.WebSocketConnection;
import com.github.jenya705.mcapi.module.web.websocket.WebSocketMessage;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public class WebSocketRouteContainerImpl<T extends WebSocketContainerConnection> implements WebSocketRouteContainer<T> {

    private final Map<WebSocketConnection, T> webSocketConnections = new HashMap<>();
    private Function<WebSocketConnection, T> factoryMethod;

    public WebSocketRouteContainerImpl() { }

    public WebSocketRouteContainerImpl(Function<WebSocketConnection, T> factoryMethod) {
        setFactoryMethod(factoryMethod);
    }

    public WebSocketRouteContainerImpl(Supplier<T> factoryMethod) {
        setFactoryMethod(factoryMethod);
    }

    public void setFactoryMethod(Function<WebSocketConnection, T> factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    public void setFactoryMethod(Supplier<T> factoryMethod) {
        this.factoryMethod = it -> factoryMethod.get();
    }

    @Override
    public Object onMessage(WebSocketConnection connection, WebSocketMessage message) {
        return getConnection(connection).onMessage(message);
    }

    @Override
    public void onConnect(WebSocketConnection connection) {
        T localConnection = factoryMethod.apply(connection);
        webSocketConnections.put(connection, localConnection);
        localConnection.delegate(connection);
        localConnection.onConnection();
    }

    @Override
    public void onClose(WebSocketConnection connection) {
        getConnection(connection).onClose();
    }

    @Override
    public void onError(WebSocketConnection connection, Throwable throwable) {
        getConnection(connection).onError(throwable);
    }

    @Override
    public Collection<T> getConnections() {
        return webSocketConnections.values();
    }

    private T getConnection(WebSocketConnection connection) {
        if (!webSocketConnections.containsKey(connection)) {
            throw new IllegalArgumentException("This connection is not registered");
        }
        return webSocketConnections.get(connection);
    }

}
