package com.github.jenya705.mcapi.server.module.mapper;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.Vector3;
import com.github.jenya705.mcapi.entity.EntityError;
import com.github.jenya705.mcapi.server.defaults.DefaultValueProcessor;
import com.github.jenya705.mcapi.server.defaults.DefaultValueProcessorImpl;
import com.github.jenya705.mcapi.server.util.CacheClassMap;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * @author Jenya705
 */
public class MapperImpl implements Mapper, JacksonProvider {

    private static final ApiError defaultError = new EntityError(0, 500, null, "Some bad happened");

    private final ObjectMapper json = new ObjectMapper();
    private final Map<Class<?>, RawDeserializer<?>> rawDeserializers = CacheClassMap.concurrent();
    private final Map<Class<?>, ThrowableParser> throwableParsers = CacheClassMap.concurrent();

    @Getter
    private final DefaultValueProcessor defaultValueProcessor = new DefaultValueProcessorImpl();

    private static double normalizeDouble(double num) {
        if (!Double.isFinite(num)) {
            throw new IllegalArgumentException("Num is NaN or Infinite");
        }
        long integerPart = (long) num;
        double fractionPart = num - integerPart;
        return integerPart + Math.round(fractionPart / Vector3.epsilon) * Vector3.epsilon;
    }

    private static float normalizeFloat(float num) {
        return (float) normalizeDouble(num);
    }

    private static boolean parseBoolean(String s) {
        if (s.length() == 1) {
            if (s.equals("1")) return true;
            else if (s.equals("0")) return false;
        }
        else if (s.equalsIgnoreCase("true")) return true;
        else if (s.equalsIgnoreCase("false")) return false;
        throw new IllegalArgumentException("String is not true or false");
    }

    private static char parseChar(String s) {
        if (s.length() == 1) return s.charAt(0);
        throw new IllegalArgumentException("String length is not 1");
    }

    public MapperImpl() {
        json.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        this
                .rawDeserializer(byte.class, Byte::parseByte)
                .rawDeserializer(short.class, Short::parseShort)
                .rawDeserializer(int.class, Integer::parseInt)
                .rawDeserializer(long.class, Long::parseLong)
                .rawDeserializer(float.class, Float::parseFloat)
                .rawDeserializer(double.class, Double::parseDouble)
                .rawDeserializer(boolean.class, MapperImpl::parseBoolean)
                .rawDeserializer(char.class, MapperImpl::parseChar)
                .rawDeserializer(Byte.class, Byte::valueOf)
                .rawDeserializer(Short.class, Short::valueOf)
                .rawDeserializer(Integer.class, Integer::valueOf)
                .rawDeserializer(Long.class, Long::valueOf)
                .rawDeserializer(Double.class, Double::valueOf)
                .rawDeserializer(Boolean.class, MapperImpl::parseBoolean)
                .rawDeserializer(Character.class, MapperImpl::parseChar)
                .rawDeserializer(String.class, s -> s)
                .jsonSerializer(float.class, (value, generator, serializers) ->
                        generator.writeRawValue(Float.toString(MapperImpl.normalizeFloat(value)))
                )
                .jsonSerializer(double.class, (value, generator, serializers) ->
                        generator.writeRawValue(Double.toString(MapperImpl.normalizeDouble(value)))
                )
                .jsonSerializer(Float.class, (value, generator, serializers) ->
                        generator.writeRawValue(Float.toString(MapperImpl.normalizeFloat(value)))
                )
                .jsonSerializer(Double.class, (value, generator, serializers) ->
                        generator.writeRawValue(Double.toString(MapperImpl.normalizeDouble(value)))
                )
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
    public ApiError asApiError(Throwable throwable) {
        if (throwableParsers.containsKey(throwable.getClass())) {
            return throwableParsers.get(throwable.getClass()).parse(throwable);
        }
        if (throwable instanceof Exception) {
            return ApiError.raw((Exception) throwable);
        }
        return defaultError;
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

    @Override
    public <T> Mapper throwableParser(Class<? extends T> clazz, ThrowableParser throwableParser) {
        throwableParsers.put(clazz, throwableParser);
        return this;
    }

    @Override
    public <T> Mapper beanSerializerModifier(Class<? extends T> clazz, BeanSerializerModifier beanSerializerModifier) {
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(beanSerializerModifier);
        json.registerModule(module);
        return this;
    }

    @Override
    public ObjectMapper getMapper() {
        return json;
    }
}
