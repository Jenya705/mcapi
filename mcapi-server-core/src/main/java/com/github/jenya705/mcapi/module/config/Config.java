package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.LoadableConfigData;
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
