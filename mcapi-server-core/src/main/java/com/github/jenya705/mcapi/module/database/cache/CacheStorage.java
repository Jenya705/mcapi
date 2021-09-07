package com.github.jenya705.mcapi.module.database.cache;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface CacheStorage {

    BotEntity getCachedBot(String token);

    BotEntity getCachedBot(int id);

    Collection<BotLinkEntity> getCachedLinks(UUID target);

    Collection<BotLinkEntity> getCachedLinks(int botId);

    Collection<BotPermissionEntity> getCachedPermissions(int botId);

    void cache(BotEntity bot);

    void cache(BotLinkEntity link);

    void cache(BotPermissionEntity permission);

    void unCache(BotEntity bot);

    void unCache(BotLinkEntity link);

    void unCache(BotPermissionEntity permission);

    default BotPermissionEntity getPermission(int botId, String permission, UUID target) {
        return Optional.ofNullable(getCachedPermissions(botId))
                .flatMap(collection -> collection
                        .stream()
                        .filter(it -> it.getPermission().equals(permission) && it.getTarget().equals(target))
                        .findAny()
                )
                .orElse(null);
    }
}
