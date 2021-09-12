package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.module.database.DatabaseStorage;
import com.github.jenya705.mcapi.module.storage.StorageModule;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BotObject implements AbstractBot {

    private final BotEntity entity;
    private final DatabaseStorage scriptStorage;
    private final StorageModule storage;

    @Override
    public List<BotLinkEntity> getLinks() {
        return scriptStorage.findLinksById(entity.getId());
    }

    @Override
    public boolean hasPermission(String permission, UUID target) {
        BotPermissionEntity permissionEntity =
                scriptStorage.findPermission(entity.getId(), permission, target);
        if (permissionEntity == null) {
            if (target == null || target.equals(BotPermissionEntity.identityTarget)) {
                PermissionEntity storagePermission = storage.getPermission(permission);
                return storagePermission != null && storagePermission.isEnabled();
            }
            else {
                return hasPermission(permission);
            }
        }
        return permissionEntity.isToggled();
    }

    @Override
    public BotEntity getEntity() {
        return entity;
    }
}
