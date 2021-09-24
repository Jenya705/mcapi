package com.github.jenya705.mcapi.module.web.reactor;

import com.github.jenya705.mcapi.module.web.websocket.WebSocketConnection;
import lombok.AllArgsConstructor;
import lombok.Setter;
import reactor.netty.http.websocket.WebsocketInbound;
import reactor.netty.http.websocket.WebsocketOutbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class ReactorWebSocketConnection implements WebSocketConnection {

    private final WebsocketInbound inbound;
    private final WebsocketOutbound outbound;
    private final ReactorServer server;

    private Consumer<String> sendFunction;

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
        if (sendFunction != null) {
            sendFunction.accept(server.getMapper().asJson(obj));
        }
        else {
            toSend.add(server.getMapper().asJson(obj));
        }
    }

    public void setSendFunction(Consumer<String> sendFunction) {
        this.sendFunction = sendFunction;
        toSend.forEach(str -> this.sendFunction.accept(str));
        toSend.clear();
    }
}
