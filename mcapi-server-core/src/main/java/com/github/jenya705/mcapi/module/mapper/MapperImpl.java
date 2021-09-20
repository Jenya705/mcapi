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
    private final Map<Class<?>, RawDeserializer<?>> rawDeserializers = new HashMap<>();

    private static boolean booleanParse(String s) {
        if (s.equalsIgnoreCase("true")) return true;
        if (s.equalsIgnoreCase("false")) return false;
        throw new IllegalArgumentException("String is not true or false");
    }

    private static char charParse(String s) {
        if (s.length() == 1) return s.charAt(0);
        throw new IllegalArgumentException("String length is not 1");
    }

    public MapperImpl() {
        this
                .rawDeserializer(byte.class, Byte::parseByte)
                .rawDeserializer(short.class, Short::parseShort)
                .rawDeserializer(int.class, Integer::parseInt)
                .rawDeserializer(long.class, Long::parseLong)
                .rawDeserializer(float.class, Float::parseFloat)
                .rawDeserializer(double.class, Double::parseDouble)
                .rawDeserializer(boolean.class, MapperImpl::booleanParse)
                .rawDeserializer(char.class, MapperImpl::charParse)
                .rawDeserializer(Byte.class, Byte::valueOf)
                .rawDeserializer(Short.class, Short::valueOf)
                .rawDeserializer(Integer.class, Integer::valueOf)
                .rawDeserializer(Long.class, Long::valueOf)
                .rawDeserializer(Float.class, Float::valueOf)
                .rawDeserializer(Double.class, Double::valueOf)
                .rawDeserializer(Boolean.class, MapperImpl::booleanParse)
                .rawDeserializer(Character.class, MapperImpl::charParse)
                .rawDeserializer(String.class, s -> s)
        ;
    }

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
        return (T) rawDeserializers.get(clazz).deserialize(param);
    }

    @Override
    public <T> Mapper rawDeserializer(Class<? extends T> clazz, RawDeserializer<T> deserializer) {
        rawDeserializers.put(clazz, deserializer);
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
