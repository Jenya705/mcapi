package com.github.jenya705.mcapi.server.command.advanced;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Global;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class AdvancedCommandExecutorConfig extends Config {

    @Value
    @Global("argumentParseFailed")
    private String argumentParseFailed = "&cFailed to parse %argument_id%";

    @Value
    @Global("notEnoughArguments")
    private String notEnoughArguments = "&cNot enough arguments";

    public AdvancedCommandExecutorConfig() { }

    public AdvancedCommandExecutorConfig(ConfigData configData) {
        load(configData);
    }

}
