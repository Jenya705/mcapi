package com.github.jenya705.mcapi.server.command;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Global;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContainerCommandConfig extends Config {

    @Value
    @Global("notPermitted")
    private String notPermittedMessage = "&cYou are not permitted to do that";

    public ContainerCommandConfig(ConfigData configData) {
        load(configData);
    }
}
