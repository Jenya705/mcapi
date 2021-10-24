package com.github.jenya705.mcapi.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface JacksonSerializer<T> {

    void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException;

}
