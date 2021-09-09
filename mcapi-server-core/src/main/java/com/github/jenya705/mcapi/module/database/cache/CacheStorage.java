package com.github.jenya705.mcapi.module.database.cache;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;

import java.util.*;

/**
 * @author Jenya705
 */
public interface CacheStorage {

    FutureCacheStorage withFuture();

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
                        .filter(it -> Objects.equals(permission, it.getPermission()) && Objects.equals(it.getTarget(), target))
                        .findAny()
                )
                .orElse(null);
    }

    default Collection<BotLinkEntity> getCachedLinksWithNullSafety(UUID target) {
        return Objects.requireNonNullElse(getCachedLinks(target), Collections.emptyList());
    }

    default Collection<BotLinkEntity> getCachedLinksWithNullSafety(int botId) {
        return Objects.requireNonNullElse(getCachedLinks(botId), Collections.emptyList());
    }

    default Collection<BotPermissionEntity> getCachedPermissionsWithNullSafety(int botId) {
        return Objects.requireNonNullElse(getCachedPermissions(botId), Collections.emptyList());
    }

}
