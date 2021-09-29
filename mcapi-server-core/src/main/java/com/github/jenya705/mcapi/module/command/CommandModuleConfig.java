package com.github.jenya705.mcapi.module.command;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.data.loadable.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class CommandModuleConfig extends Config {

    @Value
    private int maxCommandOptions = 10;

    @Value
    private String commandNameRegex = "[a-zA-Z0-9_]*";

    public CommandModuleConfig(ConfigData configData) {
        load(configData);
    }

}
