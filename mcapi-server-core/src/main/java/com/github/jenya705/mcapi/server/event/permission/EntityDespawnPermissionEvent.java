package com.github.jenya705.mcapi.server.event.permission;

import com.github.jenya705.mcapi.event.EntityDespawnEvent;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class EntityDespawnPermissionEvent implements PermissionEvent {

    private final EntityDespawnEvent event;
    private PermissionResult result = PermissionResult.DATABASE_CHECK;

}
