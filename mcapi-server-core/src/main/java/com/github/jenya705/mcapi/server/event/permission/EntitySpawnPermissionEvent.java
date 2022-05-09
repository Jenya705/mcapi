package com.github.jenya705.mcapi.server.event.permission;

import com.github.jenya705.mcapi.event.EntitySpawnEvent;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class EntitySpawnPermissionEvent implements PermissionEvent {

    private final EntitySpawnEvent event;
    private PermissionResult result = PermissionResult.DATABASE_CHECK;

}
