package dev.mcapi.modules.config;

import java.util.Map;

public interface ConfigData {

    static ConfigData fromMap(Map<? extends CharSequence, Object> map) {
        return new MapConfigData(map);
    }

    <T> T get(String key);

    ConfigData getData(String key);

}
