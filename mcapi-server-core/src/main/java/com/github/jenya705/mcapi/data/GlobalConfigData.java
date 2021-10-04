package com.github.jenya705.mcapi.data;

import com.github.jenya705.mcapi.ServerPlatform;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jenya705
 */
public class GlobalConfigData extends MapConfigData implements GlobalContainer {

    @Getter
    private final Map<String, Object> globals;

    public GlobalConfigData(Map<String, Object> data, Map<String, Object> globals) {
        super(data);
        this.globals = globals;
    }

    public GlobalConfigData(Map<String, Object> data, Map<String, Object> globals, ServerPlatform platform) {
        super(data, platform);
        this.globals = globals;
    }

    public GlobalConfigData(Map<String, Object> data, ServerPlatform platform) {
        super(data, platform);
        globals = new HashMap<>();
        data.put(globalKey, globals);
    }

    @SuppressWarnings("unchecked")
    public GlobalConfigData(Map<String, Object> data) {
        super(data);
        if (data.containsKey(globalKey)) {
            globals = (Map<String, Object>) data.get(globalKey);
        }
        else {
            globals = new HashMap<>();
            data.put(globalKey, globals);
        }
    }

    public GlobalConfigData() {
        super();
        globals = new HashMap<>();
    }

    @Override
    public GlobalContainer global(String key, Object value) {
        globals.put(key, value);
        return this;
    }

    @Override
    public MapConfigData createSelf(Map<String, Object> from) {
        return new GlobalConfigData(from, globals, getPlatform());
    }

    @Override
    public Optional<Object> global(String key) {
        return Optional.ofNullable(globals.getOrDefault(key, null));
    }

    @Override
    public Object requiredGlobal(String key, Object value) {
        return global(key)
                .orElseGet(() -> {
                    global(key, value);
                    return value;
                });
    }
}
