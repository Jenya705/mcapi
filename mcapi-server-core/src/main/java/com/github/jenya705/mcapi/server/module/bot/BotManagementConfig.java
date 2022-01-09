package com.github.jenya705.mcapi.server.module.bot;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.regex.Pattern;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class BotManagementConfig extends Config {

    @Value
    private Pattern namePattern = Pattern.compile("[a-zA-Z0-9_]*");

    public BotManagementConfig(ConfigData configData) {
        load(configData);
    }

}
