package dev.mcapi.config;

import com.google.gson.Gson;
import com.google.inject.Inject;
import dev.mcapi.config.loader.ConfigLoader;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ConfigStorageImpl implements ConfigStorage {

    private static final Gson GSON = new Gson();

    private final Map<String, ConfigData> loaded = new ConcurrentHashMap<>();

    private final ConfigLoader configLoader;

    @Override
    public ConfigData getConfig(String name) {
        return loaded.computeIfAbsent(name, this::loadConfig);
    }

    @SneakyThrows
    private ConfigData loadConfig(String name) {
        Map<String, Object> loaded = configLoader.loadConfig(name);
        try (InputStream resourceIs = getClass().getClassLoader()
                .getResourceAsStream(name + ".json");
             Reader resourceReader = resourceIs == null ?
                     null : new InputStreamReader(resourceIs)
        ) {
            Map<String, Object> result = resourceReader == null ?
                    new HashMap<>() : GSON.fromJson(resourceReader, HashMap.class);
            result.putAll(loaded);
            return new MapConfigData(result);
        }
    }

}
