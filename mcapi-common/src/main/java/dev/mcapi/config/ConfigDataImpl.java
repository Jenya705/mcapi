package dev.mcapi.config;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ConfigDataImpl implements ConfigData {

    private final Map<String, Object> data;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        int index = key.indexOf('.');
        if (index == -1) return (T) data.get(key);
        Map<String, Object> current = data;
        while (true) {
            int next = key.indexOf('.', ++index);
            if (next == -1) return (T) current.get(key.substring(index));
            Object obj = current.get(key.substring(index, next));
            index = next;
            if (!(obj instanceof Map)) throw new IllegalArgumentException("Key is wrong");
            current = (Map<String, Object>) obj;
        }
    }
}
