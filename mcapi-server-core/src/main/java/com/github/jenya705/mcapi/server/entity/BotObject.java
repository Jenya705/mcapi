package com.github.jenya705.mcapi.server.entity;

import com.github.jenya705.mcapi.server.module.database.storage.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BotObject implements AbstractBot {

    private final BotEntity entity;
    private final EventDatabaseStorage scriptStorage;
    private final StorageModule storage;

    @Override
    public List<BotLinkEntity> getLinks() {
        return scriptStorage.findLinksById(entity.getId());
    }

    @Override
    public BotPermissionEntity getPermissionEntity(String permission, UUID target) {
        return scriptStorage.findPermission(entity.getId(), permission, target);
    }

    @Override
    public BotEntity getEntity() {
        return entity;
    }
}
