package com.github.jenya705.mcapi.module.database.safe;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.module.database.cache.CacheStorage;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class CacheDatabaseGetter implements DatabaseGetter {

    private final CacheStorage cacheStorage;

    @Override
    public BotEntity getBot(String token) {
        return cacheStorage.getCachedBot(token);
    }

    @Override
    public BotEntity getBot(int id) {
        return cacheStorage.getCachedBot(id);
    }

    @Override
    public Collection<BotEntity> getBotsByOwner(UUID owner) {
        return cacheStorage.getCachedBots(owner);
    }

    @Override
    public Collection<BotLinkEntity> getLinks(UUID target) {
        return cacheStorage.getCachedLinksWithNullSafety(target);
    }

    @Override
    public Collection<BotLinkEntity> getLinks(int botId) {
        return cacheStorage.getCachedLinksWithNullSafety(botId);
    }

    @Override
    public Collection<BotPermissionEntity> getPermissions(int botId) {
        return cacheStorage.getCachedPermissionsWithNullSafety(botId);
    }

    @Override
    public BotPermissionEntity getPermission(int botId, String permission, UUID target) {
        return null;
    }
}
