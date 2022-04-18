package com.github.jenya705.mcapi.server.event.permission;

import com.github.jenya705.mcapi.server.event.NotAsyncEvent;

/**
 * @author Jenya705
 */
@NotAsyncEvent
public interface PermissionEvent {

    void setResult(PermissionResult result);

    PermissionResult getResult();

}
