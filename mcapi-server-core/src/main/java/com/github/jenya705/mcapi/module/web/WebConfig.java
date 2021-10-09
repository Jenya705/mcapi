package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.Value;
import com.github.jenya705.mcapi.module.config.Config;
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
public class WebConfig extends Config {

    @Value
    private int port = 8080;

    public WebConfig(ConfigData configData) {
        load(configData);
    }

}
