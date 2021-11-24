package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.CallbackLoadableConfigData;
import com.github.jenya705.mcapi.log.TimerTask;
import com.github.jenya705.mcapi.module.rest.ObjectTunnelFunction;
import com.github.jenya705.mcapi.util.CacheClassMap;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
public class ConfigModuleImpl extends AbstractApplicationModule implements ConfigModule {

    private ConfigData config;
    private GlobalConfig global;

    private final Map<Class<?>, ObjectTunnelFunction<Object, ?>> deserializers = new CacheClassMap<>();
    private final Map<Class<?>, ObjectTunnelFunction<?, Object>> serializers = new CacheClassMap<>();

    @SuppressWarnings("unchecked")
    @OnInitializing(priority = 0)
    public void initialize() throws IOException {
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
        config = createConfig(core().loadConfig("config"));
        task.complete();
        global = new GlobalConfig(config.required("global"));
        app().setDebug(
                config
                        .getBoolean("debug")
                        .orElse(false)
        );
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
        return new CallbackLoadableConfigData(data, new LoadableConfigModuleSerializerImpl(this));
    }

    @Override
    public <T> void addDeserializer( Class<? extends T> clazz, ObjectTunnelFunction<Object, T> tunnelFunction) {
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
