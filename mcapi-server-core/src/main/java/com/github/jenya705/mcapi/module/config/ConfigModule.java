package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.module.rest.ObjectTunnelFunction;

import java.util.Map;

/**
 * @author Jenya705
 */
public interface ConfigModule {

    ConfigData getConfig();

    GlobalConfig global();

    ConfigData createConfig(Map<String, Object> data);

    <T> void addDeserializer(ObjectTunnelFunction<Object, T> tunnelFunction, Class<? extends T> clazz);

    <T> T deserialize(Object value, Class<? extends T> clazz);

}
