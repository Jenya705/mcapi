package com.github.jenya705.mcapi.server.module.permission;

import com.github.jenya705.mcapi.event.EntityDespawnEvent;
import com.github.jenya705.mcapi.event.EntitySpawnEvent;
import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.google.inject.ImplementedBy;

/**
 *
 * Manager of permissions. It invokes events which can change result of permission.
 *
 * @author Jenya705
 */
@ImplementedBy(PermissionManagerImpl.class)
public interface PermissionManager {

    boolean hasPermissionInventoryMove(AbstractBot bot, InventoryMoveEvent event);

    boolean hasPermissionSpawnEntity(AbstractBot bot, EntitySpawnEvent event);

    boolean hasPermissionDespawnEntity(AbstractBot bot, EntityDespawnEvent event);

}
