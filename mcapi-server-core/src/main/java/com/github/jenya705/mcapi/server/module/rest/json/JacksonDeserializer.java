package com.github.jenya705.mcapi.server.module.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author Jenya705
 */
public class JacksonDeserializer<T> extends StdDeserializer<T> {

    private final JsonDeserializerFunction<T> deserializerFunction;

    public JacksonDeserializer(Class<?> vc, JsonDeserializerFunction<T> deserializerFunction) {
        super(vc);
        this.deserializerFunction = deserializerFunction;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return deserializerFunction.deserialize(p, ctxt);
    }
}
