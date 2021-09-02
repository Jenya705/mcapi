package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.data.ConfigData;
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
