package com.github.jenya705.mcapi.module.mapper;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.module.rest.ObjectTunnelFunction;
import com.github.jenya705.mcapi.module.rest.json.JsonUtils;

/**
 * @author Jenya705
 */
public interface Mapper {

    String asJson(Object obj);

    ApiError asApiError(Throwable throwable);

    <T> T fromJson(String json, Class<? extends T> clazz);

    <T> T fromRaw(String param, Class<? extends T> clazz);

    <T> Mapper rawDeserializer(Class<? extends T> clazz, RawDeserializer<T> deserializer);

    <T> Mapper jsonDeserializer(Class<T> clazz, JsonDeserializer<? extends T> deserializer);

    <T> Mapper jsonSerializer(Class<? extends T> clazz, JsonSerializer<T> serializer);

    <T> Mapper throwableParser(Class<? extends T> clazz, ThrowableParser throwableParser);

    default <T, E> Mapper tunnelJsonDeserializer(Class<E> returnClass, Class<T> inputClass, ObjectTunnelFunction<T, ? extends E> tunnelFunction) {
        return jsonDeserializer(returnClass, JsonUtils.jacksonDeserializerTunnel(inputClass, tunnelFunction));
    }

    default <T, E> Mapper tunnelJsonSerializer(Class<T> inputClass, ObjectTunnelFunction<T, E> tunnelFunction) {
        return jsonSerializer(inputClass, JsonUtils.jacksonSerializerTunnel(inputClass, tunnelFunction));
    }

}
