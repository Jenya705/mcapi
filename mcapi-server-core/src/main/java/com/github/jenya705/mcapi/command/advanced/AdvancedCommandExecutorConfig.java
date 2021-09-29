package com.github.jenya705.mcapi.command.advanced;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.data.loadable.Global;
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
public class AdvancedCommandExecutorConfig extends Config {

    @Value
    @Global("argumentParseFailed")
    private String argumentParseFailed = "&cFailed to parse %argument_id%";

    @Value
    @Global("notEnoughArguments")
    private String notEnoughArguments = "&cNot enough arguments";

    protected AdvancedCommandExecutorConfig() { }

    public AdvancedCommandExecutorConfig(ConfigData configData) {
        load(configData);
    }

}
