package com.github.jenya705.mcapi.command.bot.create;

import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.Global;
import com.github.jenya705.mcapi.module.config.Java;
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
    @Java("&aSuccess! Created bot with token <click:suggest_command:%token%>&e%token%</click>")
    private String success = "&aSuccess! Created bot with token &e%token%";

    @Value
    @Global("playerNotFound")
    private String playerNotFound = "&cGiven player is not exist nor you are not player";

    @Value
    @Global("botNameTooLong")
    private String botNameTooLong = "&cBot name too long, maximum length 64";

    @Value
    private String notPermittedForOthers = "&cYou can not create bots for others";

    @Value
    private String botWithNameExist = "&cChoose another name, because this used";

    public CreateBotConfig(ConfigData data) {
        load(data);
    }
}
