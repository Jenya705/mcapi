package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.CallbackLoadableConfigData;
import com.github.jenya705.mcapi.module.rest.ObjectTunnelFunction;
import com.github.jenya705.mcapi.util.CacheClassMap;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
public class ConfigModuleImpl extends AbstractApplicationModule implements ConfigModule {

    private ConfigData config;
    private GlobalConfig global;

    private final CacheClassMap<ObjectTunnelFunction<Object, ?>> deserializers = new CacheClassMap<>();

    @OnInitializing(priority = 1)
    public void initialize() throws IOException {
        log.info("Loading config...");
        config = createConfig(core().loadConfig("config"));
        log.info("Done! (Loading config...)");
        global = new GlobalConfig(config.required("global"));
    }

    @OnDisable(priority = 4)
    @OnStartup(priority = 4)
    public void save() throws IOException {
        core().saveConfig("config", config.primitiveRepresent());
    }

    @Override
    public GlobalConfig global() {
        return global;
    }

    @Override
    public ConfigData createConfig(Map<String, Object> data) {
        return new CallbackLoadableConfigData(data, app().getPlatform(), this::deserialize);
    }

    @Override
    public <T> void addDeserializer(ObjectTunnelFunction<Object, T> tunnelFunction, Class<? extends T> clazz) {
        deserializers.put(clazz, tunnelFunction);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Object value, Class<? extends T> clazz) {
        ObjectTunnelFunction<Object, ?> function = deserializers.get(clazz);
        if (function == null) return null;
        return (T) function.tunnel(value);
    }
}
