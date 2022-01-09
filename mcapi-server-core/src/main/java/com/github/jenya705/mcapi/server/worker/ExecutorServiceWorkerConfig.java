package com.github.jenya705.mcapi.server.worker;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class ExecutorServiceWorkerConfig extends Config {

    @Value
    private int threads = 4;

    public ExecutorServiceWorkerConfig(ConfigData configData) {
        load(configData);
    }

}
