package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.ServerPlatform;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.GlobalContainer;
import com.github.jenya705.mcapi.data.PlatformContainer;
import com.github.jenya705.mcapi.data.loadable.Global;
import com.github.jenya705.mcapi.data.loadable.Java;
import com.github.jenya705.mcapi.data.loadable.LoadableConfigData;
import com.github.jenya705.mcapi.data.loadable.Value;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Optional;

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
