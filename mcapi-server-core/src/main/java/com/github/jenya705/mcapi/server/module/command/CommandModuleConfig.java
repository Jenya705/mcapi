package com.github.jenya705.mcapi.server.module.command;

import com.github.jenya705.mcapi.server.command.ContainerCommandConfig;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.regex.Pattern;

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
    private Pattern commandNamePattern = Pattern.compile("[a-zA-Z0-9_]*");

    @Value
    private ContainerCommandConfig container = new ContainerCommandConfig();

    @Value
    private AdvancedCommandExecutorConfig command = new AdvancedCommandExecutorConfig();

    public CommandModuleConfig(ConfigData configData) {
        load(configData);
    }

}
