package com.github.jenya705.mcapi.data;

import lombok.AllArgsConstructor;

import java.util.HashMap;
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
        return getObject(key)
                .filter(it -> it instanceof String)
                .map(it -> (String) it);
    }

    @Override
    public Optional<Integer> getInteger(String key) {
        return getObject(key)
                .filter(it -> it instanceof Integer)
                .map(it -> (Integer) it);
    }

    @Override
    public Optional<Boolean> getBoolean(String key) {
        return getObject(key)
                .filter(it -> it instanceof Boolean)
                .map(it -> (Boolean) it);
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
                ConfigData directory = createSelf((Map<String, Object>) obj);
                set(key, directory);
                return Optional.of(directory);
            }
        }
        return Optional.empty();
    }

    @Override
    public ConfigData required(String key) {
        return required(key, createSelf(new HashMap<>()));
    }

    @Override
    public void set(String key, Object value) {
        data.put(key, value);
    }

    @Override
    public Map<String, Object> represent() {
        return data;
    }

    public MapConfigData createSelf(Map<String, Object> from) {
        return new MapConfigData(from);
    }
}
