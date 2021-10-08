package com.github.jenya705.mcapi.module.web.gateway;

import com.github.jenya705.mcapi.GatewayAuthorizationRequest;
import com.github.jenya705.mcapi.SubscribeRequest;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.RestSubscribeRequest;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.AuthorizationFormatException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.web.websocket.stateful.SimpleStatefulWebSocketConnection;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jenya705
 */
public class DefaultGatewayClient extends SimpleStatefulWebSocketConnection<Integer> implements GatewayClient, BaseCommon {

    private static final String permissionFormat = "gateway.%s";

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    private final AuthorizationModule authorizationModule;

    @Getter
    private final Set<String> subscriptions = new HashSet<>();

    @Getter
    private AbstractBot owner;

    public DefaultGatewayClient(ServerApplication application) {
        this.application = application;
        authorizationModule = bean(AuthorizationModule.class);
        this
                .defaultState(0)
                .state(0, message -> {
                    GatewayAuthorizationRequest authorizationRequest =
                            message.as(GatewayAuthorizationRequest.class);
                    authorization(authorizationRequest.getToken());
                    changeState(1);
                    return null;
                }) // Authorization
                .state(1, message -> {
                    SubscribeRequest subscribeRequest =
                            message.as(SubscribeRequest.class);
                    List<String> failed = new ArrayList<>();
                    for (String subscription : subscribeRequest.getSubscriptions()) {
                        if (!subscribe(subscription)) failed.add(subscription);
                    }
                    return new RestSubscribeRequest(failed.toArray(String[]::new));
                }) // Subscriptions
        ;
    }

    @Override
    public void authorization(String token) {
        owner = authorizationModule.rawBot(token);
    }

    @Override
    public boolean subscribe(String subscription) {
        String gatewayPermission = String.format(permissionFormat, subscription);
        return owner != null &&
                owner.hasPermission(gatewayPermission) &&
                subscriptions.add(subscription);
    }

    @Override
    public boolean isSubscribed(String subscription) {
        return subscriptions.contains(subscription);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof AuthorizationBadTokenException || e instanceof AuthorizationFormatException) {
            close();
        }
    }
}
