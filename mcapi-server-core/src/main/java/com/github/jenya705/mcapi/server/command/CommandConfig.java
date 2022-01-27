package com.github.jenya705.mcapi.server.command;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.module.config.Config;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@ToString
@Singleton
public class CommandConfig extends Config {



    public CommandConfig(ConfigData configData) {
        load(configData);
    }

}
