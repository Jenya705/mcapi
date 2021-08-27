package com.github.jenya705.mcapi.command.bot;

import com.github.jenya705.mcapi.command.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.data.ConfigData;
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
public class CreateBotConfig extends AdvancedCommandExecutorConfig {

    @Value
    private String success = "&aSuccess! Created bot with token &e%token%";

    @Value
    private String playerNotFound = "&cGiven player is not exist nor you are not player";

    @Value
    private String botNameTooLong = "&cBot name too long, maximum length 64";

    @Value
    private String notPermittedForOthers = "&cYou can not create bots for others";

    public CreateBotConfig(ConfigData data) {
        load(data);
    }
}
