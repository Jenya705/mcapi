package com.github.jenya705.mcapi.server.module.permission;

import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.event.permission.InventoryMovePermissionEvent;
import com.github.jenya705.mcapi.server.event.permission.PermissionEvent;
import com.github.jenya705.mcapi.server.event.permission.PermissionResult;
import com.github.jenya705.mcapi.server.inventory.InventoryMoveEventHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

/**
 * @author Jenya705
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PermissionManagerImpl implements PermissionManager {

    private final EventLoop eventLoop;

    @Override
    public boolean hasPermissionInventoryMove(AbstractBot bot, InventoryMoveEvent event) {
        PermissionEvent permissionEvent = new InventoryMovePermissionEvent(event);
        eventLoop.invoke(permissionEvent);
        if (permissionEvent.getResult() == PermissionResult.DATABASE_CHECK) {
            return InventoryMoveEventHandler.testClient(bot, event);
        }
        return permissionEvent.getResult() == PermissionResult.ALLOW;
    }
}
