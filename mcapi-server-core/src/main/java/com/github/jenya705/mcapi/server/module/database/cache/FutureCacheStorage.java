package com.github.jenya705.mcapi.server.module.database.cache;

import com.github.jenya705.mcapi.server.entity.BotLinkEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface FutureCacheStorage extends CacheStorage {

    Collection<BotLinkEntity> getCachedLinksAndCacheBots(UUID target);

    Collection<BotLinkEntity> getCachedLinksAndCacheBots(int botId);

    default Collection<BotLinkEntity> getCachedLinksAndCacheBotsWithNullSafety(UUID target) {
        return Objects.requireNonNullElse(getCachedLinksAndCacheBots(target), Collections.emptyList());
    }

    default Collection<BotLinkEntity> getCachedLinksAndCacheBotsWithNullSafety(int botId) {
        return Objects.requireNonNullElse(getCachedLinksAndCacheBots(botId), Collections.emptyList());
    }
}
