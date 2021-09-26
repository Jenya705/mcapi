package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.module.web.websocket.WebSocketConnection;
import lombok.Getter;
import reactor.core.publisher.FluxSink;
import reactor.netty.http.websocket.WebsocketInbound;
import reactor.netty.http.websocket.WebsocketOutbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Jenya705
 */
public class ReactorWebSocketConnection implements WebSocketConnection {

    private final WebsocketInbound inbound;
    private final WebsocketOutbound outbound;
    private final ReactorServer server;

    @Getter
    private FluxSink<String> sink;

    private final List<String> toSend = new ArrayList<>();

    public ReactorWebSocketConnection(WebsocketInbound inbound, WebsocketOutbound outbound, ReactorServer server) {
        this.inbound = inbound;
        this.outbound = outbound;
        this.server = server;
    }

    @Override
    public <T> Optional<T> header(String key, Class<? extends T> clazz) {
        return Optional.ofNullable(
                server
                        .getMapper()
                        .fromRaw(inbound.headers().get(key), clazz)
        );
    }

    @Override
    public void close() {
        outbound.sendClose();
    }

    @Override
    public void send(Object obj) {
        if (sink != null) {
            sink.next(server.getMapper().asJson(obj));
        }
        else {
            toSend.add(server.getMapper().asJson(obj));
        }
    }

    @Override
    public int hashCode() {
        return inbound.hashCode();
    }

    public synchronized void setSink(FluxSink<String> sink) {
        this.sink = sink;
        toSend.forEach(str -> this.sink.next(str));
        toSend.clear();
    }
}
