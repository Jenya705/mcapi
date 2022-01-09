package com.github.jenya705.mcapi.server.module.config;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.LoadableConfigData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jenya705
 */
@Slf4j
public class Config {

    public void load(ConfigData data) {
        if (data instanceof LoadableConfigData) {
            ((LoadableConfigData) data).load(this);
        }
    }
}
