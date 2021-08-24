package com.github.jenya705.mcapi.data;

import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class MapConfigData implements ConfigData {

    private final Map<String, Object> data;

    public MapConfigData() {
        this(new LinkedHashMap<>());
    }

    @Override
    public Optional<Object> getObject(String key) {
        return data.containsKey(key) ? Optional.of(data.get(key)) : Optional.empty();
    }

    @Override
    public Optional<String> getString(String key) {
        if (data.containsKey(key)) {
            Object obj = data.get(key);
            return obj instanceof String ? Optional.of((String) obj) : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getInteger(String key) {
        if (data.containsKey(key)) {
            Object obj = data.get(key);
            return obj instanceof Integer ? Optional.of((Integer) obj) : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> getBoolean(String key) {
        if (data.containsKey(key)) {
            Object obj = data.get(key);
            return obj instanceof Boolean ? Optional.of((Boolean) obj) : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<ConfigData> getDirectory(String key) {
        if (data.containsKey(key)) {
            Object obj = data.get(key);
            if (obj instanceof ConfigData) {
                return Optional.of((ConfigData) obj);
            }
            else if (obj instanceof Map) {
                ConfigData directory = new MapConfigData((Map<String, Object>) obj);
                set(key, directory);
                return Optional.of(directory);
            }
        }
        return Optional.empty();
    }

    @Override
    public void set(String key, Object value) {
        data.put(key, value);
    }

    @Override
    public Map<String, Object> represent() {
        return data;
    }
}
