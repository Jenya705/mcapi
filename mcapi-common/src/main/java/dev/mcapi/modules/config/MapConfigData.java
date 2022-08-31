package dev.mcapi.modules.config;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
class MapConfigData implements ConfigData {

    private final Map<? extends CharSequence, Object> map;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Map<? extends CharSequence, Object> currentMap = map;
        int previous;
        int index = 0;
        while (true) {
            previous = index;
            index = key.indexOf('.', index + 1);
            CharSequence currentKey = key.subSequence(previous, index);
            Object obj = currentMap.get(currentKey);
            boolean hasNext = index == key.length();
            if (!hasNext) return (T) obj;
            currentMap = (Map<? extends CharSequence, Object>) obj;
        }
    }

    @Override
    public ConfigData getData(String key) {
        return new MapConfigData(get(key));
    }
}
