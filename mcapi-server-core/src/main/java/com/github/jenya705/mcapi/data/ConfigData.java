package com.github.jenya705.mcapi.data;

import java.util.Map;
import java.util.Optional;

/**
 * @author Jenya705
 */
public interface ConfigData {

    Optional<Object> getObject(String key);

    Optional<String> getString(String key);

    Optional<Integer> getInteger(String key);

    Optional<Boolean> getBoolean(String key);

    Optional<ConfigData> getDirectory(String key);

    void set(String key, Object value);

    default Object requiredObject(String key, Object value) {
        return getObject(key)
                .orElseGet(() -> {
                    set(key, value);
                    return value;
                });
    }

    default String required(String key, String value) {
        return getString(key)
                .orElseGet(() -> {
                    set(key, value);
                    return value;
                });
    }

    default Integer required(String key, Integer value) {
        return getInteger(key)
                .orElseGet(() -> {
                    set(key, value);
                    return value;
                });
    }

    default Boolean required(String key, Boolean value) {
        return getBoolean(key)
                .orElseGet(() -> {
                    set(key, value);
                    return value;
                });
    }

    default ConfigData required(String key, ConfigData value) {
        return getDirectory(key)
                .orElseGet(() -> {
                    set(key, value);
                    return value;
                });
    }

    ConfigData required(String key);

    Map<String, Object> represent();

    default Map<String, Object> primitiveRepresent() {
        Map<String, Object> represent = represent();
        for (Map.Entry<String, Object> entry: represent.entrySet()) {
            if (entry.getValue() instanceof ConfigData) {
                represent.put(entry.getKey(), ((ConfigData) entry.getValue()).primitiveRepresent());
            }
        }
        return represent;
    }

}
