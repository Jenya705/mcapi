package com.github.jenya705.mcapi.app;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.InternalException;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.TunnelClient;
import com.github.jenya705.mcapi.error.*;
import com.github.jenya705.mcapi.reactor.rest.HttpRestClient;
import com.github.jenya705.mcapi.reactor.tunnel.HttpTunnelClient;
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
    private final List<Runnable> startHandlers = new ArrayList<>();

    private final RestClient restClient;
    private final TunnelClient tunnelClient;

    private final String ip;
    private final int port;
    private final String token;

    private boolean started;

    public DefaultLibraryApplication(String ip, int port, String token) {
       this.ip = ip;
       this.port = port;
       this.token = token;

       restClient = new HttpRestClient(this);
       tunnelClient = new HttpTunnelClient(this);

       MCAPIErrorRegisterer.registerAllErrors(
               this,
               AuthorizationBadTokenException.class,
               AuthorizationFormatException.class,
               BadOptionException.class,
               BadUuidFormatException.class,
               BodyIsEmptyException.class,
               BotCommandNotExistException.class,
               BotNotPermittedException.class,
               CommandNameFormatException.class,
               CommandOptionsAllException.class,
               JsonDeserializeException.class,
               LinkRequestExistException.class,
               LinkRequestPermissionIsGlobalException.class,
               LinkRequestPermissionNotFoundException.class,
               MessageTypeNotExistException.class,
               MessageTypeNotSupportException.class,
               PlayerIdFormatException.class,
               PlayerNotFoundException.class,
               SelectorEmptyException.class,
               TooManyOptionsException.class
       );

    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void start() {
        if (isStarted()) return;
        startHandlers.forEach(Runnable::run);
        startHandlers.clear();
        started = true;
    }

    @Override
    public void onStart(Runnable runnable) {
        startHandlers.add(runnable);
    }

    @Override
    public RestClient rest() {
        return restClient;
    }

    @Override
    public TunnelClient tunnel() {
        return tunnelClient;
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
        if (namespaceBuilders == null || namespaceBuilders.size() < error.getCode()) {
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
        for (int i = namespaceBuilders.size(); i < code; ++i) {
            namespaceBuilders.add(null);
        }
        namespaceBuilders.set(code - 1, builder);
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
