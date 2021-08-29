package com.github.jenya705.mcapi.gateway;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketListener;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Slf4j
public class GatewayApplication extends WebSocketApplication {

    @Override
    public WebSocket createSocket(ProtocolHandler handler, HttpRequestPacket requestPacket, WebSocketListener... listeners) {
        return new GatewayClientImpl(handler, requestPacket, listeners);
    }

    private GatewayClientImpl parse(WebSocket socket) {
        if (!(socket instanceof GatewayClientImpl)) {
            log.warn("WebSocket is not instanceof GatewayClient. Socket: {}", socket);
            return null;
        }
        return (GatewayClientImpl) socket;
    }

    @Override
    public void onMessage(WebSocket socket, byte[] bytes) {
        GatewayClientImpl client = parse(socket);
        if (client == null) return;
        client.onMessage(bytes);
    }

    @Override
    public void onMessage(WebSocket socket, String text) {
        GatewayClientImpl client = parse(socket);
        if (client == null) return;
        client.onMessage(text);
    }

    public void broadcast(GatewayObject<?> gatewayObject) {
        getWebSockets().forEach(webSocket -> {
            GatewayClientImpl client = parse(webSocket);
            if (client == null || !client.isSubscribed(gatewayObject.getType())) return;
            client.send(gatewayObject);
        });
    }

    public List<GatewayClientImpl> getClients() {
        return getWebSockets()
                .stream()
                .filter(it -> it instanceof GatewayClientImpl)
                .map(it -> (GatewayClientImpl) it)
                .filter(client -> client.getCurrentState() == GatewayState.LISTENING && client.getEntity() != null)
                .collect(Collectors.toList());
    }

}
