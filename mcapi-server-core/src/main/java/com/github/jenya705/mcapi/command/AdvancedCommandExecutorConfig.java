package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.module.config.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public abstract class AdvancedCommandExecutorConfig extends Config {

    @Value
    private String argumentParseFailed = "&cFailed to parse %argument_id%";
    @Value
    private String notEnoughArguments = "&cNot enough arguments";

}
