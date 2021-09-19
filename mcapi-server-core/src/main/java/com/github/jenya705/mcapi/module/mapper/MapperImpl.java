package com.github.jenya705.mcapi.module.mapper;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
public class MapperImpl implements Mapper {

    private final ObjectMapper json = new ObjectMapper();
    private final Map<Class<?>, RawDeserializer<?>> paramDeserializers = new HashMap<>();

    @Override
    @SneakyThrows
    public String asJson(Object obj) {
        return json.writeValueAsString(obj);
    }

    @Override
    @SneakyThrows
    public <T> T fromJson(String jsonContent, Class<? extends T> clazz) {
        return json.readValue(jsonContent, clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromRaw(String param, Class<? extends T> clazz) {
        if (clazz == String.class) return (T) param;
        return (T) paramDeserializers.get(clazz).deserialize(param);
    }

    @Override
    public <T> Mapper rawDeserializer(Class<? extends T> clazz, RawDeserializer<T> deserializer) {
        paramDeserializers.put(clazz, deserializer);
        return this;
    }

    @Override
    public <T> Mapper jsonDeserializer(Class<T> clazz, JsonDeserializer<? extends T> deserializer) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(clazz, deserializer);
        json.registerModule(module);
        return this;
    }

    @Override
    public <T> Mapper jsonSerializer(Class<? extends T> clazz, JsonSerializer<T> serializer) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(clazz, serializer);
        json.registerModule(module);
        return this;
    }
}
