package com.github.jenya705.mcapi.gateway;

import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JacksonProvider;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.util.ZipUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.DefaultWebSocket;
import org.glassfish.grizzly.websockets.ProtocolHandler;
import org.glassfish.grizzly.websockets.WebSocketListener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jenya705
 */
public class GatewayClientImpl extends DefaultWebSocket implements BaseCommon, GatewayClient {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);

    @Getter
    @Setter
    private GatewayState currentState = GatewayState.AUTHORIZATION;

    @Delegate
    private AbstractBot bot;

    private final Set<String> subscriptions = new HashSet<>();

    public GatewayClientImpl(ProtocolHandler protocolHandler, HttpRequestPacket request, WebSocketListener... listeners) {
        super(protocolHandler, request, listeners);
    }

    @Override
    public void authorization(String token) {
        bot = authorizationModule.rawBot(token);
    }

    @Override
    public boolean subscribe(String to) {
        if (isSubscribed(to)) {
            return false;
        }
        if (bot.hasPermission("gateway." + to)) {
            subscriptions.add(to);
            return true;
        }
        return false;
    }

    @Override
    public boolean isSubscribed(String subscription) {
        return subscriptions.contains(subscription);
    }

    @Override
    public void onMessage(byte[] data) {
        try {
            onMessage(new String(ZipUtils.decompress(data)));
        } catch (Exception e) {
            send(ApiError.raw(e));
        }
    }

    @Override
    public void onMessage(String text) {
        try {
            currentState.getConsumer().accept(this, text);
        } catch (Exception e) {
            send(ApiError.raw(e));
        }
    }

    @Override
    public GrizzlyFuture<DataFrame> send(byte[] data) {
        return super.send(data);
    }

    @Override
    @SneakyThrows
    public GrizzlyFuture<DataFrame> send(String data) {
        return send(ZipUtils.compress(data.getBytes()));
    }

    @SneakyThrows
    public GrizzlyFuture<DataFrame> send(Object obj) {
        return send(JacksonProvider
                .getMapper()
                .writeValueAsString(obj)
        );
    }
}
