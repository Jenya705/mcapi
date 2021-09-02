package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.data.ConfigData;

/**
 * @author Jenya705
 */
public interface ConfigModule {

    ConfigData getConfig();

    GlobalConfig global();

}
