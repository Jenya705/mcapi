package com.github.jenya705.mcapi.server.module.authorization.debug;

import com.github.jenya705.mcapi.server.entity.PermissionEntity;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.google.inject.Inject;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class DefaultPermissionManager implements PermissionManager {

    private final StorageModule storageModule;

    @Inject
    public DefaultPermissionManager(StorageModule storageModule) {
        this.storageModule = storageModule;
    }

    @Override
    public boolean hasPermission(String permission, UUID target) {
        return storageModule
                .permission(permission)
                .map(PermissionEntity::isEnabled)
                .orElse(false);
    }
}
