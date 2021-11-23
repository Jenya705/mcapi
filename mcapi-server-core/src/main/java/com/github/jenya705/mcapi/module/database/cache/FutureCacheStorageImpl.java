package com.github.jenya705.mcapi.module.database.cache;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.module.database.DatabaseModule;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public class FutureCacheStorageImpl extends AbstractApplicationModule implements FutureCacheStorage {

    private final CacheStorage cacheStorage;
    private final DatabaseModule databaseModule;

    public FutureCacheStorageImpl(ServerApplication application, CacheStorage cacheStorage, DatabaseModule databaseModule) {
        super(application);
        this.cacheStorage = cacheStorage;
        this.databaseModule = databaseModule;
    }

    @Override
    public FutureCacheStorage withFuture() {
        return this;
    }

    @Override
    public BotEntity getCachedBot(String token) {
        BotEntity bot = cacheStorage.getCachedBot(token);
        if (bot == null) {
            worker().invoke(() ->
                    cacheStorage.cache(
                            databaseModule
                                    .storage()
                                    .findBotByToken(token)
                    )
            );
        }
        return bot;
    }

    @Override
    public BotEntity getCachedBot(int id) {
        BotEntity bot = cacheStorage.getCachedBot(id);
        if (bot == null) {
            worker().invoke(() ->
                    cacheStorage.cache(
                            databaseModule
                                    .storage()
                                    .findBotById(id)
                    )
            );
        }
        return bot;
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinks(UUID target) {
        return getCachedLinksOrApply(target, cacheStorage::cache);
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinks(int botId) {
        return getCachedLinksOrApply(botId, cacheStorage::cache);
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinksAndCacheBots(UUID target) {
        return getCachedLinksOrApply(target, generateCacheLinksAndBotConsumer());
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinksAndCacheBots(int botId) {
        return getCachedLinksOrApply(botId, generateCacheLinksAndBotConsumer());
    }

    private Consumer<BotLinkEntity> generateCacheLinksAndBotConsumer() {
        return it -> {
            cacheStorage.cache(it);
            if (cacheStorage.getCachedBot(it.getBotId()) == null) {
                cacheStorage.cache(
                        databaseModule
                                .storage()
                                .findBotById(it.getBotId())
                );
            }
        };
    }

    private Collection<BotLinkEntity> getCachedLinksOrApply(UUID target, Consumer<BotLinkEntity> linkConsumer) {
        Collection<BotLinkEntity> links = cacheStorage.getCachedLinks(target);
        if (links == null) {
            worker().invoke(() ->
                    databaseModule
                            .storage()
                            .findLinksByTarget(target)
                            .forEach(linkConsumer)
            );
        }
        return links;
    }

    private Collection<BotLinkEntity> getCachedLinksOrApply(int botId, Consumer<BotLinkEntity> linkConsumer) {
        Collection<BotLinkEntity> links = cacheStorage.getCachedLinks(botId);
        if (links == null) {
            worker().invoke(() ->
                    databaseModule
                            .storage()
                            .findLinksById(botId)
                            .forEach(linkConsumer)
            );
        }
        return links;
    }

    @Override
    public Collection<BotPermissionEntity> getCachedPermissions(int botId) {
        Collection<BotPermissionEntity> permissions = cacheStorage.getCachedPermissions(botId);
        if (permissions == null) {
            worker().invoke(() ->
                    databaseModule
                            .storage()
                            .findPermissionsById(botId)
                            .forEach(cacheStorage::cache)
            );
        }
        return permissions;
    }

    @Override
    public void cache(BotEntity bot) {
        cacheStorage.cache(bot);
    }

    @Override
    public void cache(BotLinkEntity link) {
        cacheStorage.cache(link);
    }

    @Override
    public void cache(BotPermissionEntity permission) {
        cacheStorage.cache(permission);
    }

    @Override
    public void unCache(BotEntity bot) {
        cacheStorage.unCache(bot);
    }

    @Override
    public void unCache(BotLinkEntity link) {
        cacheStorage.unCache(link);
    }

    @Override
    public void unCache(BotPermissionEntity permission) {
        cacheStorage.unCache(permission);
    }
}
