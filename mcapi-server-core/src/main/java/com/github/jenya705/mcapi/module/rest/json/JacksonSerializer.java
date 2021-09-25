package com.github.jenya705.mcapi.module.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author Jenya705
 */
public class JacksonSerializer<T> extends StdSerializer<T> {

    private final JsonSerializerFunction<T> serializerFunction;

    public JacksonSerializer(Class<T> t, JsonSerializerFunction<T> serializerFunction) {
        super(t);
        this.serializerFunction = serializerFunction;
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        serializerFunction.serialize(value, gen, provider);
    }
}
