package com.github.jenya705.mcapi.module.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface JsonSerializerFunction<T> {

    void serialize(T value, JsonGenerator generator, SerializerProvider serializers) throws IOException;

}
