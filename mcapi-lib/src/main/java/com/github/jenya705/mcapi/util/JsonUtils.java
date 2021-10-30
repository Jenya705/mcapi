package com.github.jenya705.mcapi.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author Jenya705
 */
@UtilityClass
public class JsonUtils {

    public <T> StdDeserializer<T> getDeserializer(Class<? extends T> clazz, JacksonDeserializer<T> deserializer) {
        return new StdDeserializer<>(clazz) {
            @Override
            public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return deserializer.deserialize(p, ctxt);
            }
        };
    }

    public <T> StdSerializer<T> getSerializer(Class<T> clazz, JacksonSerializer<T> serializer) {
        return new StdSerializer<>(clazz) {
            @Override
            public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                serializer.serialize(value, gen, provider);
            }
        };
    }

    public <T, V> StdDeserializer<V> getDeserializer(Class<? extends T> from, Class<? extends V> to, Function<T, V> function) {
        return getDeserializer(to, (p, ctxt) -> function.apply(p.getCodec().readValue(p, from)));
    }

    public <T, V> StdSerializer<T> getSerializer(Class<T> from, Class<? extends V> to, Function<T, V> function) {
        return getSerializer(from, (value, gen, provider) -> gen.writeObject(function.apply(value)));
    }

}
