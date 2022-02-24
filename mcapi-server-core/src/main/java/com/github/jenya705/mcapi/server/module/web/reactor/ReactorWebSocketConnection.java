package com.github.jenya705.mcapi.server.module.web.reactor;

import com.github.jenya705.mcapi.server.module.web.websocket.WebSocketConnection;
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

    private List<String> toSend = new ArrayList<>();

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
        sendRaw(server.getMapper().asJson(obj));
    }

    @Override
    public void sendRaw(String str) {
        if (sink != null) {
            sink.next(str);
        }
        else {
            toSend.add(str);
        }
    }

    public void setSink(FluxSink<String> sink) {
        this.sink = sink;
        toSend.forEach(this.sink::next);
        toSend = null;
    }
}
