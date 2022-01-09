package com.github.jenya705.mcapi.server.module.web.tunnel;

import com.github.jenya705.mcapi.server.BaseCommon;
import com.github.jenya705.mcapi.server.Bean;
import com.github.jenya705.mcapi.server.OnStartup;
import com.github.jenya705.mcapi.server.ServerApplication;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.web.WebServer;
import com.github.jenya705.mcapi.server.module.web.websocket.container.WebSocketRouteContainerImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @author Jenya705
 */
@Slf4j
public class DefaultEventTunnel extends WebSocketRouteContainerImpl<DefaultEventTunnelClient> implements EventTunnel, BaseCommon {

    @Bean
    private ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    @OnStartup
    public void start() {
        TimerTask task = TimerTask.start(log, "Starting event tunnel...");
        setFactoryMethod(() -> new DefaultEventTunnelClient(app()));
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
