package com.github.jenya705.mcapi.test.mock;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.ConfigModuleImpl;

import java.util.Map;

/**
 * @author Jenya705
 */
public class MockConfigModule extends ConfigModuleImpl {

    public void joinConfig(ConfigData configData) {
        joinConfig(getConfig(), configData);
    }

    private void joinConfig(ConfigData to, ConfigData what) {
        for (Map.Entry<String, Object> entry: what.represent().entrySet()) {
            if (entry.getValue() instanceof ConfigData) {
                joinConfig(to.required(entry.getKey()), (ConfigData) entry.getValue());
            }
            else {
                to.set(entry.getKey(), entry.getValue());
            }
        }
    }

}
