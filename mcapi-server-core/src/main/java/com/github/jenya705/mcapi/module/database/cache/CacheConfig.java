package com.github.jenya705.mcapi.module.database.cache;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.module.config.Value;
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
    private int botCacheSize = 10;

    @Value
    private int botLinksCacheSize = 10;

    @Value
    private int targetLinksCacheSize = 10;

    @Value
    private int permissionCacheSize = 10;

    public CacheConfig(ConfigData configData) {
        load(configData);
    }
}
