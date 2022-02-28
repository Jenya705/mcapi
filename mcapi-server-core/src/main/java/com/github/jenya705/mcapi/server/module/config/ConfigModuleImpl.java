package com.github.jenya705.mcapi.server.module.config;

import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.application.OnDisable;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.CallbackLoadableConfigData;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.rest.ObjectTunnelFunction;
import com.github.jenya705.mcapi.server.util.CacheClassMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Jenya705
 */
@Getter
@Singleton
public class ConfigModuleImpl implements ConfigModule {

    private final ConfigData config;
    private final GlobalConfig global;
    private final ServerCore core;
    private final Logger log;

    private final Map<Class<?>, ObjectTunnelFunction<Object, ?>> deserializers = CacheClassMap.concurrent();
    private final Map<Class<?>, ObjectTunnelFunction<?, Object>> serializers = CacheClassMap.concurrent();

    @Inject
    @SuppressWarnings("unchecked")
    public ConfigModuleImpl(ServerCore core, Logger log) throws IOException {
        this.core = core;
        this.log = log;
        this
                .raw(String.class)
                .raw(byte.class)
                .raw(short.class)
                .raw(int.class)
                .raw(long.class)
                .raw(float.class)
                .raw(double.class)
                .raw(boolean.class)
                .raw(Byte.class)
                .raw(Short.class)
                .raw(Integer.class)
                .raw(Long.class)
                .raw(Float.class)
                .raw(Double.class)
                .raw(Boolean.class)
                .raw(Map.class)
                .raw(List.class)
                .serializer(Pattern.class, Pattern::pattern)
                .deserializer(Pattern.class, obj -> Pattern.compile(String.valueOf(obj)))
                .rawSerializer(ConfigData.class)
                .deserializer(ConfigData.class, obj -> createConfig((Map<String, Object>) obj))
        ;
        TimerTask task = TimerTask.start(log, "Loading config...");
        config = createConfig(core.loadConfig("config"));
        task.complete();
        global = new GlobalConfig(config.required("global"));
    }

    @OnDisable(priority = 4)
    @OnStartup(priority = 4)
    public void save() throws IOException {
        core.saveConfig("config", config.primitiveRepresent());
    }

    @Override
    public GlobalConfig global() {
        return global;
    }

    @Override
    public ConfigData createConfig(Map<String, Object> data) {
        return new CallbackLoadableConfigData(data, new LoadableConfigModuleSerializerImpl(this));
    }

    @Override
    public <T> void addDeserializer(Class<? extends T> clazz, ObjectTunnelFunction<Object, T> tunnelFunction) {
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

    @Override
    public <T> void addSerializer(Class<? extends T> clazz, ObjectTunnelFunction<T, Object> tunnelFunction) {
        serializers.put(clazz, tunnelFunction);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> Object serialize(T value, Class<? extends T> clazz) {
        ObjectTunnelFunction<T, Object> function =
                (ObjectTunnelFunction<T, Object>) serializers.get(clazz);
        if (function == null) return null;
        return function.tunnel(value);
    }

}
