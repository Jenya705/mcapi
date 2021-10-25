package com.github.jenya705.mcapi.app;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.InternalException;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.reactor.HttpRestClient;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
@Getter
public class DefaultLibraryApplication implements LibraryApplication {

    private final Map<String, List<Function<ApiError, RuntimeException>>> exceptionBuilders = new HashMap<>();
    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final RestClient restClient;

    private final String ip;
    private final int port;
    private final String token;

    public DefaultLibraryApplication(String ip, int port, String token) {
       this.ip = ip;
       this.port = port;
       this.token = token;

       restClient = new HttpRestClient(this);
    }

    @Override
    public RestClient rest() {
        return restClient;
    }

    @Override
    @SneakyThrows
    public <T> T fromJson(String json, Class<? extends T> clazz) {
        return jsonMapper.readValue(json, clazz);
    }

    @Override
    @SneakyThrows
    public String asJson(Object obj) {
        return jsonMapper.writeValueAsString(obj);
    }

    @Override
    public RuntimeException buildException(ApiError error) {
        List<Function<ApiError, RuntimeException>> namespaceBuilders =
                exceptionBuilders.getOrDefault(error.getNamespace(), null);
        if (namespaceBuilders == null || namespaceBuilders.size() > error.getCode()) {
            // unknown exception
            return new InternalException(error.getReason());
        }
        Function<ApiError, RuntimeException> builderFunction =
                namespaceBuilders.get(error.getCode() - 1);
        if (builderFunction == null) {
            // unknown exception
            return new InternalException(error.getReason());
        }
        return builderFunction.apply(error);
    }

    @Override
    public void addExceptionBuilder(String namespace, int code, Function<ApiError, RuntimeException> builder) {
        exceptionBuilders.putIfAbsent(namespace, new ArrayList<>());
        List<Function<ApiError, RuntimeException>> namespaceBuilders = exceptionBuilders.get(namespace);
        for (int i = namespaceBuilders.size() + 1; i < code; ++i) {
            namespaceBuilders.add(null);
        }
        namespaceBuilders.add(builder);
    }

    @Override
    public <T> void addSerializer(Class<? extends T> clazz, JsonSerializer<T> serializer) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(clazz, serializer);
        jsonMapper.registerModule(simpleModule);
    }

    @Override
    public <T> void addDeserializer(Class<T> clazz, JsonDeserializer<? extends T> deserializer) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(clazz, deserializer);
        jsonMapper.registerModule(simpleModule);
    }
}
