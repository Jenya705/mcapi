package com.github.jenya705.mcapi.server.module.web.tunnel;

import com.github.jenya705.mcapi.EventTunnelAuthorizationRequest;
import com.github.jenya705.mcapi.SubscribeRequest;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.AuthorizationFormatException;
import com.github.jenya705.mcapi.rest.RestSubscribeRequest;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.module.web.websocket.stateful.SimpleStatefulWebSocketConnection;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Jenya705
 */
public class DefaultEventTunnelClient extends SimpleStatefulWebSocketConnection<Integer> implements EventTunnelClient, BaseCommon {

    private static final String permissionPrefix = "event_tunnel.";
    private static final String permissionSuffix = ".listen";

    private static final String permissionFormat = permissionPrefix + "%s" + permissionSuffix;

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    private final AuthorizationModule authorizationModule;

    @Getter
    private final Set<String> subscriptions = new CopyOnWriteArraySet<>();

    private final AtomicReference<AbstractBot> owner = new AtomicReference<>();

    public DefaultEventTunnelClient(ServerApplication application, AuthorizationModule authorizationModule) {
        this.application = application;
        this.authorizationModule = authorizationModule;
        this
                .defaultState(0)
                .state(0, message -> {
                    EventTunnelAuthorizationRequest authorizationRequest =
                            message.as(EventTunnelAuthorizationRequest.class);
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
        owner.set(authorizationModule.rawBot(token));
    }

    @Override
    public boolean subscribe(String subscription) {
        String gatewayPermission = String.format(permissionFormat, subscription);
        return getOwner() != null &&
                getOwner().hasPermission(gatewayPermission) &&
                subscriptions.add(subscription);
    }

    @Override
    public boolean isSubscribed(String subscription) {
        return subscriptions.contains(subscription);
    }

    public void recalculate() {
        Set<String> oldSubscriptions = new HashSet<>(subscriptions);
        subscriptions.clear();
        List<String> failed = new ArrayList<>();
        for (String subscription: oldSubscriptions) {
            if (!subscribe(subscription)) failed.add(subscription);
        }
        send(new RestSubscribeRequest(failed.toArray(String[]::new)));
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof AuthorizationBadTokenException || e instanceof AuthorizationFormatException) {
            close();
        }
    }

    @Override
    public AbstractBot getOwner() {
        return owner.get();
    }
}
