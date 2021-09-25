package com.github.jenya705.mcapi.module.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface JsonDeserializerFunction<T> {

    T deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException;

}
