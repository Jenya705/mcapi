package com.github.jenya705.mcapi.server.data.loadable;

import com.github.jenya705.mcapi.server.data.ConfigData;

/**
 * @author Jenya705
 */
public interface CallbackLoadableConfigDataSerializer {

    Object serialize(Object obj, ConfigData data, String name);

    Object deserialize(Object obj, Class<?> type, ConfigData data, String name);

}
