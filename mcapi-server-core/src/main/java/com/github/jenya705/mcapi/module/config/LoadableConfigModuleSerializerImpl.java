package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.data.loadable.CallbackLoadableConfigDataSerializer;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class LoadableConfigModuleSerializerImpl implements CallbackLoadableConfigDataSerializer {

    private final ConfigModule configModule;

    @Override
    public Object serialize(Object obj) {
        return configModule.serialize(obj, obj.getClass());
    }

    @Override
    public Object deserialize(Object obj, Class<?> type) {
        return configModule.deserialize(obj, type);
    }
}
