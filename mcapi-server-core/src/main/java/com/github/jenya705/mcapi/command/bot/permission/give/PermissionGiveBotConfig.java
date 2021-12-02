package com.github.jenya705.mcapi.command.bot.permission.give;

import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.data.ConfigData;
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
public class PermissionGiveBotConfig extends AdvancedCommandExecutorConfig {

    @Value
    @Global("notPermitted")
    private String notPermitted = "&cYou are not permitted to do that";

    @Value
    private String success = "&aSuccess";

    @Value
    @Global("botNotExist")
    private String botNotExist = "&cBot with given name is not exist";

    @Value
    @Global("tooManyBots")
    private String tooManyBots = "&cToo many bots with given name, please provide the token";

    public PermissionGiveBotConfig(ConfigData data) {
        load(data);
    }

}

