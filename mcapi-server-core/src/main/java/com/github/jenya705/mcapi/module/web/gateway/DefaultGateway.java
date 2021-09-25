package com.github.jenya705.mcapi.module.web.gateway;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.module.web.websocket.container.WebSocketRouteContainerImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @author Jenya705
 */
@Slf4j
public class DefaultGateway extends WebSocketRouteContainerImpl<DefaultGatewayClient> implements Gateway, BaseCommon {

    @Bean
    private ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    public DefaultGateway() {

    }

    @OnStartup
    public void start() {
        log.info("Starting gateway...");
        setFactoryMethod(() -> new DefaultGatewayClient(app()));
        bean(WebServer.class).addWebSocketHandler("/gateway", this);
        log.info("Done! (Starting gateway...)");
    }

    @Override
    public void broadcast(Object obj, String type) {
        getClients()
                .stream()
                .filter(client -> client.isSubscribed(type))
                .forEach(client -> client.send(obj));
    }

    @Override
    public Collection<? extends GatewayClient> getClients() {
        return getConnections();
    }
}
