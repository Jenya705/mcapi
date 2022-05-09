package com.github.jenya705.mcapi.server.module.permission;

import com.github.jenya705.mcapi.event.EntityDespawnEvent;
import com.github.jenya705.mcapi.event.EntitySpawnEvent;
import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.event.permission.*;
import com.github.jenya705.mcapi.server.inventory.InventoryMoveEventHandler;
import com.github.jenya705.mcapi.server.module.web.listener.EntitySpawningListener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PermissionManagerImpl implements PermissionManager {

    private final EventLoop eventLoop;

    @Override
    public boolean hasPermissionInventoryMove(AbstractBot bot, InventoryMoveEvent event) {
        return hasPermission(
                new InventoryMovePermissionEvent(event),
                () -> InventoryMoveEventHandler.testClient(bot, event)
        );
    }

    @Override
    public boolean hasPermissionSpawnEntity(AbstractBot bot, EntitySpawnEvent event) {
        return hasPermission(
                new EntitySpawnPermissionEvent(event),
                () -> EntitySpawningListener.testClient(bot, event)
        );
    }

    @Override
    public boolean hasPermissionDespawnEntity(AbstractBot bot, EntityDespawnEvent event) {
        return hasPermission(
                new EntityDespawnPermissionEvent(event),
                () -> EntitySpawningListener.testClient(bot, event)
        );
    }

    private boolean hasPermission(PermissionEvent event, Supplier<Boolean> databaseCheck) {
        eventLoop.invoke(event);
        if (event.getResult() == PermissionResult.DATABASE_CHECK) {
            return databaseCheck.get();
        }
        return event.getResult() == PermissionResult.ALLOW;
    }

}
