package com.github.jenya705.mcapi.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface JacksonDeserializer<T> {

    T deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException;

}
