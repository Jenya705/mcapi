package com.github.jenya705.mcapi.server.module.web.tunnel;

import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.module.web.WebServer;
import com.github.jenya705.mcapi.server.module.web.websocket.container.WebSocketRouteContainerImpl;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @author Jenya705
 */
@Slf4j
@Singleton
public class DefaultEventTunnel extends WebSocketRouteContainerImpl<DefaultEventTunnelClient> implements EventTunnel, BaseCommon {

    private final ServerApplication application;
    private final AuthorizationModule authorizationModule;

    @Override
    public ServerApplication app() {
        return application;
    }

    @Inject
    public DefaultEventTunnel(ServerApplication application, AuthorizationModule authorizationModule) {
        this.application = application;
        this.authorizationModule = authorizationModule;
    }

    @OnStartup
    public void start() {
        TimerTask task = TimerTask.start(log, "Starting event tunnel...");
        setFactoryMethod(() -> new DefaultEventTunnelClient(app(), authorizationModule));
        bean(WebServer.class).addWebSocketHandler("/tunnel", this);
        task.complete();
    }

    @Override
    public void broadcast(Object obj, String type) {
        getClients()
                .stream()
                .filter(client -> client.isSubscribed(type))
                .forEach(client -> client.send(obj));
    }

    @Override
    public Collection<? extends EventTunnelClient> getClients() {
        return getConnections();
    }
}
