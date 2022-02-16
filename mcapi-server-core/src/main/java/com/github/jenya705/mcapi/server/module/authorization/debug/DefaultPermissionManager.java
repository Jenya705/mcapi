package com.github.jenya705.mcapi.server.module.authorization.debug;

import com.github.jenya705.mcapi.server.entity.PermissionEntity;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class DefaultPermissionManager implements PermissionManager {

    private final StorageModule storageModule;
    private final Map<String, Boolean> overridePermissions;

    @Override
    public boolean hasPermission(String permission, UUID target) {
        if (overridePermissions.containsKey(permission)) {
            return overridePermissions.get(permission);
        }
        return storageModule
                .permission(permission)
                .map(PermissionEntity::isEnabled)
                .orElse(false);
    }
}
