package com.github.jenya705.mcapi.module.storage;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.entity.EntityType;
import com.github.jenya705.mcapi.entity.PermissionEntity;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.permission.DefaultPermission;
import com.github.jenya705.mcapi.permission.Permissions;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
public class StorageModuleImpl extends AbstractApplicationModule implements StorageModule {

    private final Map<String, PermissionEntity> permissions = new HashMap<>();

    public StorageModuleImpl() {
        for (DefaultPermission defaultPermission : DefaultPermission.values()) {
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
        for (VanillaMaterial material: VanillaMaterial.values()) {
            addPermissionsIfNotExist(
                    true, true,
                    Permissions.BLOCK_GET + "." + material.getKey(),
                    Permissions.BLOCK_DATA + "." + material.getKey()
            );
            addPermissionsIfNotExist(
                    true, false,
                    Permissions.BLOCK_DATA_FIELD + "." + material.getKey()
            );
        }
        addEntityPermissions();
    }

    @OnStartup(priority = 4)
    @OnDisable(priority = 4)
    public void loadAndSave() throws IOException {
        StorageModuleConfig config = new StorageModuleConfig(
                bean(ConfigModule.class).createConfig(
                        core().loadConfig("storage")
                )
        );
        loadPermissions(config);
        core().saveConfig("storage", config.represent());
    }

    protected void loadPermissions(StorageModuleConfig config) {
        Map<String, Boolean> configPermissions = new LinkedHashMap<>();
        for (Map.Entry<String, PermissionEntity> permissionEntry : permissions.entrySet()) {
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

    private void addPermissionsIfNotExist(boolean global, boolean enabled, String... permissions) {
        for (String permission: permissions) {
            if (!this.permissions.containsKey(permission)) {
                addPermission(new PermissionEntity(
                        permission,
                        global, enabled
                ));
            }
        }
    }

    @SneakyThrows
    private void addEntityPermissions() {
        for (Field field: EntityType.class.getFields()) {
            if (!field.getType().equals(String.class)) continue;
            String entityType = (String) field.get(null);
            addPermissionsIfNotExist(
                    true, true,
                    Permissions.ENTITY_GET + "." + entityType
            );
        }
    }

}
