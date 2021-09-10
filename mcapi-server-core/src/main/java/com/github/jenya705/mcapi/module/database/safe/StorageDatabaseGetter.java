package com.github.jenya705.mcapi.module.database.safe;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.module.database.DatabaseStorage;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class StorageDatabaseGetter implements DatabaseGetter {

    private final DatabaseStorage databaseStorage;

    @Override
    public BotEntity getBot(String token) {
        return databaseStorage.findBotByToken(token);
    }

    @Override
    public BotEntity getBot(int id) {
        return databaseStorage.findBotById(id);
    }

    @Override
    public Collection<BotLinkEntity> getLinks(UUID target) {
        return databaseStorage.findLinksByTarget(target);
    }

    @Override
    public Collection<BotLinkEntity> getLinks(int botId) {
        return databaseStorage.findLinksById(botId);
    }

    @Override
    public Collection<BotPermissionEntity> getPermissions(int botId) {
        return databaseStorage.findPermissionsById(botId);
    }

    @Override
    public BotPermissionEntity getPermission(int botId, String permission, UUID target) {
        return databaseStorage.findPermission(botId, permission, target);
    }
}
