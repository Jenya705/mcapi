package com.github.jenya705.mcapi.server.data;

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
        return new GlobalConfigData(from, globals);
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
