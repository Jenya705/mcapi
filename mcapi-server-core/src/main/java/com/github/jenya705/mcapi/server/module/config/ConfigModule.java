package com.github.jenya705.mcapi.server.module.config;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.module.rest.ObjectTunnelFunction;

import java.util.Map;

/**
 * @author Jenya705
 */
public interface ConfigModule {

    ConfigData getConfig();

    GlobalConfig global();

    ConfigData createConfig(Map<String, Object> data);

    <T> void addDeserializer(Class<? extends T> clazz, ObjectTunnelFunction<Object, T> tunnelFunction);

    <T> void addSerializer(Class<? extends T> clazz, ObjectTunnelFunction<T, Object> tunnelFunction);

    default <T> ConfigModule serializer(Class<? extends T> clazz, ObjectTunnelFunction<T, Object> tunnelFunction) {
        addSerializer(clazz, tunnelFunction);
        return this;
    }

    default <T> ConfigModule deserializer(Class<? extends T> clazz, ObjectTunnelFunction<Object, T> tunnelFunction) {
        addDeserializer(clazz, tunnelFunction);
        return this;
    }

    default <T> ConfigModule rawSerializer(Class<? extends T> clazz) {
        addSerializer(clazz, obj -> obj);
        return this;
    }

    default <T> ConfigModule rawDeserializer(Class<? extends T> clazz) {
        addDeserializer(clazz, obj -> obj);
        return this;
    }

    default <T> ConfigModule raw(Class<? extends T> clazz) {
        addSerializer(clazz, obj -> obj);
        addDeserializer(clazz, obj -> obj);
        return this;
    }

    <T> T deserialize(Object value, Class<? extends T> clazz);

    <T> Object serialize(T value, Class<? extends T> clazz);

}
