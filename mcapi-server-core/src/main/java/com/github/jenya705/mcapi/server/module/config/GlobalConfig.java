package com.github.jenya705.mcapi.server.module.config;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class GlobalConfig extends Config {

    @Value
    private boolean botNameUnique = true;

    public GlobalConfig(ConfigData configData) {
        load(configData);
    }
}
