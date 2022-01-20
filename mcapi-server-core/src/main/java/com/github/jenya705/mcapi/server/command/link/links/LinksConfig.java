package com.github.jenya705.mcapi.server.command.link.links;

import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Global;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class LinksConfig extends AdvancedCommandExecutorConfig {

    @Value
    private String listLayout = "&eLinks of player %player%\n%list%\n&ePage %page%";

    @Value
    @Global("linkListElementRepresentation")
    private String listElement = "&7- &e%name% &7- <click:run_command:/mcapi linkMenu permission %bot_id%>&9[Permissions]</click> <click:run_command:/mcapi linkMenu unlink %bot_id%>&c[Unlink]</click>";

    @Value
    @Global("listsDelimiter")
    private String listDelimiter = "&r\n";

    @Value
    @Global("maxElementsInList")
    private int maxElements = 10;

    @Value
    @Global("playerNotFound")
    private String playerNotFound = "&cGiven player is not exist nor you are not player";

    public LinksConfig(ConfigData configData) {
        load(configData);
    }
}