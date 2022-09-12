package dev.mcapi.mapper;

import dev.mcapi.data.ErrorData;

public interface Mapper {

    <T> T fromJson(String json, Class<T> clazz);

    <T> T fromRaw(String raw, Class<T> clazz);

    ErrorData fromThrowable(Throwable throwable);

    String toJson(Object obj);

    String toRaw(Object obj);

    <T> Mapper rawSerializer(Class<T> clazz, RawSerializer<T> serializer);

    <T> Mapper rawDeserializer(Class<T> clazz, RawDeserializer<T> deserializer);

    <T extends Throwable> Mapper errorSerializer(Class<T> clazz, ErrorSerializer<T> serializer);

}
