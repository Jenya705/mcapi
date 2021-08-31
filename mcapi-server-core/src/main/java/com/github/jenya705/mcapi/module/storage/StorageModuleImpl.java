package com.github.jenya705.mcapi.module.storage;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.DefaultPermission;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.data.MapConfigData;
import com.github.jenya705.mcapi.entity.PermissionEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
public class StorageModuleImpl implements StorageModule, BaseCommon {

    private final Map<String, PermissionEntity> permissions = new HashMap<>();

    public StorageModuleImpl() {
        for (DefaultPermission defaultPermission: DefaultPermission.values()) {
            if (defaultPermission.isSelector()) {
                addPermissionWithSelectors(new PermissionEntity(
                        defaultPermission.getName(),
                        defaultPermission.isGlobal(),
                        defaultPermission.isEnabledDefault()
                ));
            }
            else {
                addPermission(new PermissionEntity(
                        defaultPermission.getName(),
                        defaultPermission.isGlobal(),
                        defaultPermission.isEnabledDefault()
                ));
            }
        }
    }

    @OnStartup(priority = 4)
    @OnDisable(priority = 4)
    public void loadAndSave() throws IOException {
        StorageModuleConfig config = new StorageModuleConfig(
                new MapConfigData(
                        core().loadConfig("storage")
                )
        );
        loadPermissions(config);
        core().saveConfig("storage", config.represent());
    }

    protected void loadPermissions(StorageModuleConfig config) {
        Map<String, Boolean> configPermissions = new LinkedHashMap<>();
        for (Map.Entry<String, PermissionEntity> permissionEntry: permissions.entrySet()) {
            if (config.getPermissions().containsKey(permissionEntry.getKey())) {
                boolean permissionEnabled = config
                        .getPermissions()
                        .get(permissionEntry.getKey());
                configPermissions.put(
                        permissionEntry.getKey(), permissionEnabled
                );
                permissions
                        .get(permissionEntry.getKey())
                        .setEnabled(permissionEnabled);
            }
            else {
                configPermissions.put(
                        permissionEntry.getKey(),
                        permissionEntry.getValue().isEnabled()
                );
            }
        }
        config.setPermissions(configPermissions);
    }

    @Override
    public PermissionEntity getPermission(String permission) {
        return permissions.getOrDefault(permission, null);
    }

    @Override
    public void addPermission(PermissionEntity permissionEntity) {
        if (permissionEntity.getPermission().length() > 256) {
            throw new IllegalArgumentException("Permission name too long");
        }
        permissions.put(permissionEntity.getPermission(), permissionEntity);
    }

    @Override
    public void addPermissionWithSelectors(PermissionEntity permissionEntity) {
        addPermission(permissionEntity);
        addPermission(new PermissionEntity(permissionEntity.getPermission() + ".@a", true, permissionEntity.isEnabled()));
        addPermission(new PermissionEntity(permissionEntity.getPermission() + ".@r", true, permissionEntity.isEnabled()));
        addPermission(new PermissionEntity(permissionEntity.getPermission() + ".@l", true, permissionEntity.isEnabled()));
    }
}
