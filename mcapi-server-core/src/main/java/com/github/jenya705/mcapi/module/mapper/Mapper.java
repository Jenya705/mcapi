package com.github.jenya705.mcapi.module.mapper;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * @author Jenya705
 */
public interface Mapper {

    String asJson(Object obj);

    <T> T fromJson(String json, Class<? extends T> clazz);

    <T> T fromRaw(String param, Class<? extends T> clazz);

    <T> Mapper rawDeserializer(Class<? extends T> clazz, RawDeserializer<T> deserializer);

    <T> Mapper jsonDeserializer(Class<T> clazz, JsonDeserializer<? extends T> deserializer);

    <T> Mapper jsonSerializer(Class<? extends T> clazz, JsonSerializer<T> serializer);

}
