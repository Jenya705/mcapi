package com.github.jenya705.mcapi.server.module.database.cache;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class CacheConfig extends Config {

    @Value
    private int botCacheSize = 500;

    @Value
    private int botLinksCacheSize = 500;

    @Value
    private int targetLinksCacheSize = 500;

    @Value
    private int permissionCacheSize = 500;

    public CacheConfig(ConfigData configData) {
        load(configData);
    }
}
