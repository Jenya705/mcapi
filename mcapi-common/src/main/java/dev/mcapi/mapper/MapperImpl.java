package dev.mcapi.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import dev.mcapi.data.ErrorData;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class MapperImpl implements Mapper {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final Map<Class<?>, RawSerializer<Object>> rawSerializers = new ConcurrentHashMap<>();
    private final Map<Class<?>, RawDeserializer<Object>> rawDeserializers = new ConcurrentHashMap<>();
    private final Map<Class<?>, ErrorSerializer<Throwable>> errorSerializers = new ConcurrentHashMap<>();

    @Override
    @SneakyThrows
    public <T> T fromJson(String json, Class<T> clazz) {
        return jsonMapper.readValue(json, clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromRaw(String raw, Class<T> clazz) {
        RawDeserializer<Object> deserializer = rawDeserializers.get(clazz);
        if (deserializer == null) {
            throw new IllegalStateException("Found no raw deserializer for " + clazz);
        }
        return (T) deserializer.deserialize(raw);
    }

    @Override
    public ErrorData fromThrowable(Throwable throwable) {
        ErrorSerializer<Throwable> serializer = errorSerializers.get(throwable.getClass());
        if (serializer == null) {
            throw new IllegalStateException("Found not error serializers for " + throwable.getClass());
        }
        return serializer.serialize(throwable);
    }

    @Override
    @SneakyThrows
    public String toJson(Object obj) {
        return jsonMapper.writeValueAsString(obj);
    }

    @Override
    public String toRaw(Object obj) {
        RawSerializer<Object> serializer = rawSerializers.get(obj.getClass());
        if (serializer == null) {
            throw new IllegalArgumentException("Found no raw serializer for " + obj.getClass());
        }
        return serializer.serialize(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Mapper rawSerializer(Class<T> clazz, RawSerializer<T> serializer) {
        rawSerializers.put(clazz, value -> serializer.serialize((T) value));
        return this;
    }

    @Override
    public <T> Mapper rawDeserializer(Class<T> clazz, RawDeserializer<T> deserializer) {
        rawDeserializers.put(clazz, deserializer::deserialize);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Throwable> Mapper errorSerializer(Class<T> clazz, ErrorSerializer<T> serializer) {
        errorSerializers.put(clazz, value -> serializer.serialize((T) value));
        return this;
    }
}
