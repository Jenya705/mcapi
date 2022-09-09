package dev.mcapi.mapper;

import dev.mcapi.data.ErrorData;

public interface ObjectMapper {

    <T> T fromJson(String json, Class<T> clazz);

    <T> T fromRaw(String raw, Class<T> clazz);

    ErrorData fromThrowable(Throwable throwable);

    String toJson(Object obj);

    String toRaw(Object obj);

}
