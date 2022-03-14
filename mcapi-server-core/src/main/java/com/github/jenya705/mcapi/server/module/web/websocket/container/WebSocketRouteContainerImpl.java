package com.github.jenya705.mcapi.server.module.web.websocket.container;

import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketConnection;
import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketMessage;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public class WebSocketRouteContainerImpl<T extends WebSocketContainerConnection> implements WebSocketRouteContainer<T> {

    @RequiredArgsConstructor
    private static class ProxyWebSocketConnection<T extends WebSocketContainerConnection>
            implements WebSocketConnection {

        private final WebSocketConnection delegate;
        private final WebSocketRouteContainerImpl<T> thatObject;

        @Override
        public <E> Optional<E> header(String key, Class<? extends E> clazz) {
            return delegate.header(key, clazz);
        }

        @Override
        public void close() {
            thatObject.removeConnection(delegate);
            delegate.close();
        }

        @Override
        public void send(Object obj) {
            delegate.send(obj);
        }

        @Override
        public void sendRaw(String str) {
            delegate.sendRaw(str);
        }
    }

    private final Map<WebSocketConnection, T> webSocketConnections = new ConcurrentHashMap<>();
    private Function<WebSocketConnection, T> factoryMethod;

    public WebSocketRouteContainerImpl() {
    }

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
        setFactoryMethod(it -> factoryMethod.get());
    }

    @Override
    public Object onMessage(WebSocketConnection connection, WebSocketMessage message) {
        return getConnection(connection).onMessage(message);
    }

    @Override
    public void onConnect(WebSocketConnection connection) {
        T localConnection = factoryMethod.apply(connection);
        webSocketConnections.put(connection, localConnection);
        localConnection.delegate(new ProxyWebSocketConnection<>(connection, this));
        localConnection.onConnection();
    }

    @Override
    public void onClose(WebSocketConnection connection) {
        removeConnection(connection).onClose();
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
        validateConnection(connection);
        return webSocketConnections.get(connection);
    }

    private T removeConnection(WebSocketConnection connection) {
        return webSocketConnections.remove(connection);
    }

    private void validateConnection(WebSocketConnection connection) {
        if (!webSocketConnections.containsKey(connection)) {
            throw new IllegalArgumentException("This connection is not registered");
        }
    }

}
