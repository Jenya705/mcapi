package com.github.jenya705.mcapi.server.module.database.cache;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Provider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class CacheStorageImpl extends AbstractApplicationModule implements CacheStorage {

    private final FutureCacheStorage futureCacheStorage;

    private final Cache<Integer, BotEntity> botCache;
    private final Cache<Integer, List<BotLinkEntity>> botLinkCache;
    private final Cache<UUID, List<BotLinkEntity>> targetLinkCache;
    private final Cache<Integer, List<BotPermissionEntity>> permissionCache;
    private final Map<String, Integer> botTokens;

    public CacheStorageImpl(ServerApplication application, CacheConfig config, DatabaseModule databaseModule) {
        super(application);
        futureCacheStorage = new FutureCacheStorageImpl(application, this, databaseModule);
        botTokens = new ConcurrentHashMap<>();
        botCache = CacheBuilder.newBuilder()
                .removalListener(notification -> {
                    BotEntity bot = (BotEntity) notification.getValue();
                    botTokens.remove(bot.getToken());
                })
                .maximumSize(config.getBotCacheSize())
                .build();
        botLinkCache = CacheBuilder.newBuilder()
                .maximumSize(config.getBotLinksCacheSize())
                .build();
        targetLinkCache = CacheBuilder.newBuilder()
                .maximumSize(config.getTargetLinksCacheSize())
                .build();
        permissionCache = CacheBuilder.newBuilder()
                .maximumSize(config.getPermissionCacheSize())
                .build();
    }

    @Override
    public FutureCacheStorage withFuture() {
        return futureCacheStorage;
    }

    @Override
    public Collection<BotEntity> getCachedBots(UUID owner) {
        return botCache
                .asMap()
                .values()
                .stream()
                .filter(it -> it.getOwner().equals(owner))
                .collect(Collectors.toList());
    }

    @Override
    public BotEntity getCachedBot(String token) {
        return getCachedBot(botTokens.getOrDefault(token, -1));
    }

    @Override
    public BotEntity getCachedBot(int id) {
        return botCache.getIfPresent(id);
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinks(UUID target) {
        return targetLinkCache.getIfPresent(target);
    }

    @Override
    public Collection<BotLinkEntity> getCachedLinks(int botId) {
        return botLinkCache.getIfPresent(botId);
    }

    @Override
    public List<BotPermissionEntity> getCachedPermissions(int botId) {
        return permissionCache.getIfPresent(botId);
    }

    @Override
    public void cache(BotEntity bot) {
        if (bot == null || bot.getToken() == null) return;
        botCache.put(bot.getId(), bot);
        botTokens.put(bot.getToken(), bot.getId());
    }

    @Override
    public void cache(BotLinkEntity link) {
        if (link == null) return;
        cacheList(targetLinkCache, link.getTarget(), link);
        cacheList(botLinkCache, link.getBotId(), link);
    }

    @Override
    public void cache(BotPermissionEntity permission) {
        if (permission == null) return;
        cacheList(permissionCache, permission.getBotId(), permission);
    }

    @Override
    public void unCache(BotEntity bot) {
        if (bot == null) return;
        botCache.asMap().remove(bot.getId());
        botTokens.remove(bot.getToken());
    }

    @Override
    public void unCache(BotLinkEntity link) {
        if (link == null) return;
        unCacheList(botLinkCache, link.getBotId(), link);
        unCacheList(targetLinkCache, link.getTarget(), link);
    }

    @Override
    public void unCache(BotPermissionEntity permission) {
        if (permission == null) return;
        unCacheList(permissionCache, permission.getBotId(), permission);
    }

    private static <T> List<T> getAddedList(List<T> list, T toAdd) {
        if (list == null) {
            List<T> newList = new ArrayList<>();
            newList.add(toAdd);
            return newList;
        }
        list.add(toAdd);
        return list;
    }

    private static <T, V> void cacheList(Cache<T, List<V>> cache, T key, V toAdd) {
        cache.put(key, getAddedList(cache.getIfPresent(key), toAdd));
    }

    private static <T, V> void unCacheList(Cache<T, List<V>> cache, T key, V toRemove) {
        Optional
                .ofNullable(cache.getIfPresent(key))
                .ifPresent(it -> it.remove(toRemove));
    }
}
