package com.github.jenya705.mcapi.command.bot.list;

import com.github.jenya705.mcapi.command.AdvancedCommandExecutorConfig;
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
public class ListBotConfig extends AdvancedCommandExecutorConfig {

    @Value
    @Global("playerNotFound")
    private String playerNotFound = "&cGiven player is not exist nor you are not player";

    @Value
    private String notPermittedForOthers = "&cYou can not see others bots";

    @Value
    private String listLayout = "&eBots of player %player_name%\n%list%\n&ePage %page%";

    @Value
    @Global("botListElementRepresentation")
    @Java("&7- &9%name% &7- <click:suggest_command:%token%>&e%token%</click>")
    private String listElement = "&7- &9%name% &7- &e%token%";

    @Value
    @Global("listsDelimiter")
    private String listDelimiter = "&r\n";

    @Value
    @Global("maxElementsInList")
    private int maxElements = 10;

    public ListBotConfig(ConfigData config) {
        load(config);
    }

}
