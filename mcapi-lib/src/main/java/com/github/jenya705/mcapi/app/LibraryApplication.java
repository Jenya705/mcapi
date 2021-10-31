package com.github.jenya705.mcapi.app;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.TunnelClient;
import com.github.jenya705.mcapi.registry.BlockDataRegistry;
import com.github.jenya705.mcapi.util.JacksonDeserializer;
import com.github.jenya705.mcapi.util.JacksonSerializer;
import com.github.jenya705.mcapi.util.JsonUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author Jenya705
 */
public interface LibraryApplication {

    static LibraryApplication create(String ip, int port, String token) {
        return new DefaultLibraryApplication(ip, port, token);
    }

    boolean isStarted();

    void start();

    void onStart(Runnable runnable);

    RestClient rest();

    TunnelClient tunnel();

    String getIp();

    int getPort();

    String getToken();

    <T> T fromJson(String json, Class<? extends T> clazz);

    String asJson(Object obj);

    RuntimeException buildException(ApiError error);

    BlockDataRegistry getBlockDataRegistry();

    void addExceptionBuilder(String namespace, int code, Function<ApiError, RuntimeException> builder);

    <T> void addSerializer(Class<? extends T> clazz, JsonSerializer<T> serializer);

    <T> void addDeserializer(Class<T> clazz, JsonDeserializer<? extends T> deserializer);

    default <T> void addSerializer(Class<T> clazz, JacksonSerializer<T> serializer) {
        addSerializer(clazz, JsonUtils.getSerializer(clazz, serializer));
    }

    default <T> void addDeserializer(Class<T> clazz, JacksonDeserializer<T> deserializer) {
        addDeserializer(clazz, JsonUtils.getDeserializer(clazz, deserializer));
    }

    default <T, V> void addSerializer(Class<T> from, Class<? extends V> to, Function<T, V> function) {
        addSerializer(from, JsonUtils.getSerializer(from, to, function));
    }

    default <T, V> void addDeserializer(Class<? extends T> from, Class<V> to, Function<T, V> function) {
        addDeserializer(to, JsonUtils.getDeserializer(from, to, function));
    }

    void stop();

}
