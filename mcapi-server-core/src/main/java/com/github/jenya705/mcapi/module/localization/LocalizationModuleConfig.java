package com.github.jenya705.mcapi.module.localization;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.module.config.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class LocalizationModuleConfig extends Config {

    @Value
    private Map<String, String> linkPermissions = new HashMap<>();

    public LocalizationModuleConfig(ConfigData configData) {
        load(configData);
    }

    public Map<String, Object> represent() {
        return Map.of(
                "linkPermissions", getLinkPermissions()
        );
    }
}
