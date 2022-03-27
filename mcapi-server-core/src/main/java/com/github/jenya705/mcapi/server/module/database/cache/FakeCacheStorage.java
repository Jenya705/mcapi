package com.github.jenya705.mcapi.server.module.database.cache;

import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Jenya705
 */
public final class FakeCacheStorage implements FutureCacheStorage {

    private final DatabaseModule databaseModule;

    public FakeCacheStorage(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
    }

    @Override
    public FutureCacheStorage withFuture() {
        return this;
    }

    @Override
    public BotEntity getCachedBot(String token) {
        return storage().findBotByToken(token);
    }

    @Override
    public BotEntity getCachedBot(int id) {
        return storage().findBotById(id);
    }

    @Override
    public Collection<BotEntity> getCachedBots(UUID owner) {
        return storage().findBotsByOwner(owner);
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinks(UUID target) {
        return storage().findLinksByTarget(target);
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinks(int botId) {
        return storage().findLinksById(botId);
    }

    @Override
    public Collection<BotPermissionEntity> getCachedPermissions(int botId) {
        return storage().findPermissionsById(botId);
    }

    @Override
    public void cache(BotEntity bot) {
        /* NOTHING */
    }

    @Override
    public void cache(BotLinkEntity link) {
        /* NOTHING */
    }

    @Override
    public void cache(BotPermissionEntity permission) {
        /* NOTHING */
    }

    @Override
    public void unCache(BotEntity bot) {
        /* NOTHING */
    }

    @Override
    public void unCache(BotLinkEntity link) {
        /* NOTHING */
    }

    @Override
    public void unCache(BotPermissionEntity permission) {
        /* NOTHING */
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinksAndCacheBots(UUID target) {
        return getCachedLinks(target);
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinksAndCacheBots(int botId) {
        return getCachedLinks(botId);
    }

    private DatabaseStorage storage() {
        return databaseModule.storage();
    }

}
