package com.github.jenya705.mcapi.server.module.mapper;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.server.defaults.DefaultValueGetter;
import com.github.jenya705.mcapi.server.module.rest.ObjectTunnelFunction;
import com.github.jenya705.mcapi.server.module.rest.json.*;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(MapperImpl.class)
public interface Mapper {

    String asJson(Object obj);

    ApiError asApiError(Throwable throwable);

    <T> T fromJson(String json, Class<? extends T> clazz);

    <T> T fromRaw(String param, Class<? extends T> clazz);

    <T> Mapper rawDeserializer(Class<? extends T> clazz, RawDeserializer<T> deserializer);

    <T> Mapper jsonDeserializer(Class<T> clazz, JsonDeserializer<? extends T> deserializer);

    <T> Mapper jsonSerializer(Class<? extends T> clazz, JsonSerializer<T> serializer);

    <T> Mapper throwableParser(Class<? extends T> clazz, ThrowableParser throwableParser);

    <T> Mapper beanSerializerModifier(Class<? extends T> clazz, BeanSerializerModifier beanSerializerModifier);

    DefaultValueGetter getDefaultValueGetter();

    default <T> Mapper jsonSerializer(Class<T> clazz, JsonSerializerFunction<T> serializerFunction) {
        return jsonSerializer(clazz, new JacksonSerializer<>(clazz, serializerFunction));
    }

    default <T> Mapper jsonDeserializer(Class<T> clazz, JsonDeserializerFunction<T> deserializerFunction) {
        return jsonDeserializer(clazz, new JacksonDeserializer<>(clazz, deserializerFunction));
    }

    default <T, E> Mapper tunnelJsonDeserializer(Class<E> returnClass, Class<T> inputClass, ObjectTunnelFunction<T, ? extends E> tunnelFunction) {
        return jsonDeserializer(returnClass, JsonUtils.jacksonDeserializerTunnel(inputClass, tunnelFunction));
    }

    default <T, E> Mapper tunnelJsonSerializer(Class<T> inputClass, ObjectTunnelFunction<T, E> tunnelFunction) {
        return jsonSerializer(inputClass, JsonUtils.jacksonSerializerTunnel(inputClass, tunnelFunction));
    }

}
