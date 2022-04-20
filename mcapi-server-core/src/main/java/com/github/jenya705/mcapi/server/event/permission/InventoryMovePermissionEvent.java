package com.github.jenya705.mcapi.server.event.permission;

import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import lombok.Data;

/**
 *
 * For asynchronous handling use: {@link InventoryMoveEvent}
 *
 * @author Jenya705
 */
@Data
public class InventoryMovePermissionEvent implements PermissionEvent {

    private final InventoryMoveEvent event;
    private PermissionResult result = PermissionResult.DATABASE_CHECK;

}
