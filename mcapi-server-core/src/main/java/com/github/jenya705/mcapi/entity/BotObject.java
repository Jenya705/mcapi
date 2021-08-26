package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.module.database.DatabaseScriptStorage;
import com.github.jenya705.mcapi.module.storage.StorageModule;
import lombok.AllArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BotObject implements AbstractBot {

    private final BotEntity entity;
    private final DatabaseScriptStorage scriptStorage;
    private final StorageModule storage;

    @Override
    public boolean hasPermission(String permission, UUID target) {
        BotPermissionEntity permissionEntity =
                scriptStorage.findPermission(entity.getId(), permission, target);
        if (permissionEntity == null) {
            return target == null || target.equals(BotPermissionEntity.identityTarget) ?
                    storage.getPermission(permission).isEnabled() :
                    hasPermission(permission);
        }
        return permissionEntity.isToggled();
    }

}
