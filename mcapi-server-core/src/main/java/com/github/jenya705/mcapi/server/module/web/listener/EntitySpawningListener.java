package com.github.jenya705.mcapi.server.module.web.listener;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.event.EntityDespawnEvent;
import com.github.jenya705.mcapi.event.EntitySpawnEvent;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.rest.event.RestEntityDespawnEvent;
import com.github.jenya705.mcapi.rest.event.RestEntitySpawnEvent;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.module.permission.PermissionManager;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class EntitySpawningListener {

    @Inject
    public EntitySpawningListener(EventTunnel eventTunnel, EventLoop eventLoop,
                                  PermissionManager permissionManager) {
        eventLoop.asyncHandler(EntitySpawnEvent.class, e ->
                eventTunnel.broadcast(
                        e, RestEntitySpawnEvent.type,
                        client -> permissionManager.hasPermissionSpawnEntity(client.getOwner(), e)
                )
        );
        eventLoop.asyncHandler(EntityDespawnEvent.class, e ->
                eventTunnel.broadcast(
                        e, RestEntityDespawnEvent.type,
                        client -> permissionManager.hasPermissionDespawnEntity(client.getOwner(), e)
                )
        );
    }

    public static boolean testClient(AbstractBot bot, EntitySpawnEvent event) {
        return testClient(bot, event.getSpawned(),
                Permissions.EVENT_TUNNEL_ENTITY_SPAWN_CAPTURED_ENTITY,
                Permissions.EVENT_TUNNEL_ENTITY_SPAWN_ENTITY
        );
    }

    public static boolean testClient(AbstractBot bot, EntityDespawnEvent event) {
        return testClient(bot, event.getDespawned(),
                Permissions.EVENT_TUNNEL_ENTITY_DESPAWN_CAPTURED_ENTITY,
                Permissions.EVENT_TUNNEL_ENTITY_DESPAWN_ENTITY
        );
    }

    private static boolean testClient(AbstractBot bot, Entity entity,
                                      String capturableEntityPermission, String entityPermission) {
        if (entity instanceof CapturableEntity &&
                ((CapturableEntity) entity).getOwner() == bot.getEntity().getId()) {
            return bot.hasPermission(capturableEntityPermission, entity.getUuid());
        }
        return bot.hasPermission(entityPermission, entity.getUuid());
    }

}
