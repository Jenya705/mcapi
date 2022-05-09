package com.github.jenya705.mcapi.server.module.web.tunnel;

import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.event.database.DatabaseUpdateDoneEvent;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.module.web.WebServer;
import com.github.jenya705.mcapi.server.module.web.websocket.container.WebSocketRouteContainerImpl;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@Singleton
public class DefaultEventTunnel extends WebSocketRouteContainerImpl<DefaultEventTunnelClient> implements EventTunnel, BaseCommon {

    private final ServerApplication application;
    private final AuthorizationModule authorizationModule;
    private final Mapper mapper;
    private final Logger log;

    @Override
    public ServerApplication app() {
        return application;
    }

    @Inject
    public DefaultEventTunnel(ServerApplication application, AuthorizationModule authorizationModule,
                              Mapper mapper, Logger log, EventLoop eventLoop) {
        this.application = application;
        this.authorizationModule = authorizationModule;
        this.mapper = mapper;
        this.log = log;
        eventLoop.asyncHandler(DatabaseUpdateDoneEvent.class, e -> {
            if (e.getEntity() instanceof BotPermissionEntity) {
                BotPermissionEntity permissionEntity = (BotPermissionEntity) e.getEntity();
                getConnections()
                        .stream()
                        .filter(client -> client.getOwner() != null &&
                                client.getOwner().getEntity().getId() == permissionEntity.getBotId()
                        )
                        .forEach(DefaultEventTunnelClient::recalculate);
            }
        });
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
        broadcast(obj, type, it -> true);
    }

    @Override
    public void broadcast(Object obj, String type, Predicate<EventTunnelClient> predicate) {
        String json = mapper.asJson(obj);
        if (debug()) {
            log.info("Broadcasting: {}", json);
        }
        getClients()
                .stream()
                .filter(client -> client.isSubscribed(type) && predicate.test(client))
                .forEach(client -> client.sendRaw(json));
    }

    @Override
    public Collection<? extends EventTunnelClient> getClients() {
        return getConnections();
    }
}
