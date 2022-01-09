package com.github.jenya705.mcapi.server.module.config;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.MapConfigData;
import com.github.jenya705.mcapi.server.data.loadable.CallbackLoadableConfigDataSerializer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class LoadableConfigModuleSerializerImpl implements CallbackLoadableConfigDataSerializer {

    private final ConfigModule configModule;

    @Override
    public Object serialize(Object obj, ConfigData data, String name) {
        return configModule.serialize(obj, obj.getClass());
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public Object deserialize(Object obj, Class<?> type, ConfigData data, String name) {
        Object endObject = obj;
        if (Config.class.isAssignableFrom(type)) {
            if (endObject instanceof Config) {
                ConfigData endObjectData = configModule.createConfig(new HashMap<>());
                ((Config) endObject).load(endObjectData);
                data.set(name, endObjectData);
                return endObject;
            }
            if (endObject instanceof Map) {
                MapConfigData mapConfigData = (MapConfigData) data;
                endObject = mapConfigData.createSelf((Map<String, Object>) endObject);
            }
            if (endObject instanceof ConfigData) {
                Constructor<?> constructor = type.getConstructor(ConfigData.class);
                return constructor.newInstance(endObject);
            }
        }
        return configModule.deserialize(endObject, type);
    }
}
