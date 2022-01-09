package com.github.jenya705.mcapi.server.command.bot.permission.list;

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
public class PermissionListBotConfig extends AdvancedCommandExecutorConfig {

    @Value
    private String listLayout = "&ePermissions for %bot_name%\n%list%\n&ePage %page%";

    @Value
    private String listElement = "&7- %toggle%%permission_name%";

    @Value
    private String permissionDisabled = "&c";

    @Value
    private String permissionEnabled = "&a";

    @Value
    @Global("listsDelimiter")
    private String listDelimiter = "&r\n";

    @Value
    @Global("botNotExist")
    private String botNotExist = "&cBot with given name is not exist";

    @Value
    @Global("tooManyBots")
    private String tooManyBots = "&cToo many bots with given name, please provide the token";

    @Value
    @Global("notPermitted")
    private String notPermitted = "&cYou are not permitted to do that";

    @Value
    @Global("maxElementsInList")
    private int maxElements = 10;

    public PermissionListBotConfig(ConfigData configData) {
        load(configData);
    }

}
