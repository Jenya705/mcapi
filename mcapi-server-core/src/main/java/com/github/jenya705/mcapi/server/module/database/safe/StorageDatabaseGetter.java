package com.github.jenya705.mcapi.server.module.database.safe;

import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
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
    public Collection<BotEntity> getBotsByOwner(UUID owner) {
        return databaseStorage.findBotsByOwner(owner);
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
