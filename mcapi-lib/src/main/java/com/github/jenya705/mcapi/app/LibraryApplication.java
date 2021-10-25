package com.github.jenya705.mcapi.app;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.util.JacksonDeserializer;
import com.github.jenya705.mcapi.util.JacksonSerializer;
import com.github.jenya705.mcapi.util.JsonUtils;

import java.util.function.Function;

/**
 * @author Jenya705
 */
public interface LibraryApplication {

    static LibraryApplication create(String ip, int port, String token) {
        return new DefaultLibraryApplication(ip, port, token);
    }

    RestClient rest();

    String getIp();

    int getPort();

    String getToken();

    <T> T fromJson(String json, Class<? extends T> clazz);

    String asJson(Object obj);

    RuntimeException buildException(ApiError error);

    void addExceptionBuilder(String namespace, int code, Function<ApiError, RuntimeException> builder);

    <T> void addSerializer(Class<? extends T> clazz, JsonSerializer<T> serializer);

    <T> void addDeserializer(Class<T> clazz, JsonDeserializer<? extends T> deserializer);

    default <T> void addSerializer(Class<T> clazz, JacksonSerializer<T> serializer) {
        addSerializer(clazz, JsonUtils.getSerializer(clazz, serializer));
    }

    default <T> void addDeserializer(Class<T> clazz, JacksonDeserializer<T> deserializer) {
        addDeserializer(clazz, JsonUtils.getDeserializer(clazz, deserializer));
    }
}
