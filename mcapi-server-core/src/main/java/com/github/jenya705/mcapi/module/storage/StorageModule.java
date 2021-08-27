package com.github.jenya705.mcapi.module.storage;

import com.github.jenya705.mcapi.entity.PermissionEntity;

import java.util.Optional;

/**
 * @author Jenya705
 */
public interface StorageModule {

    PermissionEntity getPermission(String permission);

    void addPermission(PermissionEntity permissionEntity);

    default Optional<PermissionEntity> permission(String permission) {
        return Optional.ofNullable(getPermission(permission));
    }

    default void addPermissions(PermissionEntity... permissionEntities) {
        for (PermissionEntity permissionEntity : permissionEntities) {
            addPermission(permissionEntity);
        }
    }

}