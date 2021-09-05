package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.Config;
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
public class ContainerCommandConfig extends Config {

    @Value
    @Global("notPermitted")
    private String notPermittedMessage = "&cYou are not permitted to do that";

    @Value
    private String helpLayout = "&eHelp:\n%list%";

    @Value
    @Java("&7- <click:suggest_command:%command%>&e%name%</click>")
    private String helpElement = "&7- &e%name%";

    @Value
    @Global("listsDelimiter")
    private String helpListDelimiter = "\n&r";

    public ContainerCommandConfig(ConfigData configData) {
        load(configData);
    }
}
