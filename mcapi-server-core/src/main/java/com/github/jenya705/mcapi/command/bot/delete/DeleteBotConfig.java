package com.github.jenya705.mcapi.command.bot.delete;

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
public class DeleteBotConfig extends AdvancedCommandExecutorConfig {

    @Value
    private String success = "&cBot deleted";

    @Value
    private String confirm = "&c&lWARNING! &eBot will permanently deleted! Add confirm to the command to confirm";

    @Value
    @Global("notPermitted")
    private String notPermitted = "&cYou are not permitted to do this";

    public DeleteBotConfig(ConfigData configData) {
        load(configData);
    }
}
