package dev.mcapi.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class MapConfigData implements ConfigData {

    private final Map<String, Object> map;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NotNull String key) {
        Map<String, Object> currentMap = map;
        int previousIndex = 0;
        int currentIndex = 0;
        while (true) {
            currentIndex = key.indexOf('.', currentIndex + 1);
            String currentElement = key.substring(
                    previousIndex,
                    currentIndex == -1 ? key.length() : currentIndex
            );
            Object currentObj = currentMap.get(currentElement);
            previousIndex = currentIndex;
            if (currentIndex == -1) {
                return (T) (currentObj instanceof Map ?
                        new MapConfigData((Map<String, Object>) currentObj) : currentObj
                );
            }
            if (!(currentObj instanceof Map)) {
                throw new IllegalArgumentException(
                        String.format("In path %s there is no map", key.substring(currentIndex))
                );
            }
            currentMap = (Map<String, Object>) currentObj;
        }
    }

    @Override
    @Unmodifiable
    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(map);
    }
}
