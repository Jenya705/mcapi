package com.github.jenya705.mcapi.command.link.unlink;

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
public class UnlinkConfig extends AdvancedCommandExecutorConfig {

    @Value
    @Global("playerNotFound")
    private String playerNotFound = "&cGiven player is not exist nor you are not player";

    @Value
    private String notPermittedForOthers = "&cYou can not unlink for others";

    @Value
    @Global("disabledByAdmin")
    private String disabledByAdmin = "&eThis feature disabled by admin";

    @Value
    @Global("notLinkedWithBot")
    private String notLinked = "&cYou are not linked with this bot";

    @Value
    private String success = "&cUnlinked!";

    public UnlinkConfig(ConfigData configData) {
        load(configData);
    }
}
