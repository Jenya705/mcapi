package com.github.jenya705.mcapi.module.storage;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.config.Config;
import com.github.jenya705.mcapi.module.config.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class StorageModuleConfig extends Config {

    @Value
    private Map<String, Boolean> permissions = new LinkedHashMap<>();

    public StorageModuleConfig(ConfigData data) {
        load(data);
    }

    public Map<String, Object> represent() {
        return Map.of(
                "permissions", getPermissions()
        );
    }

}
