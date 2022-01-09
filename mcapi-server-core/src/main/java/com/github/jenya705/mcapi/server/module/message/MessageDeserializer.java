package com.github.jenya705.mcapi.server.module.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.server.util.ExceptionableFunction;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface MessageDeserializer {

    default void addMessageType(String type, Class<? extends Message> messageClass) {
        addMessageType(type, messageClass, obj -> {});
    }

    <T extends Message> void addMessageType(String type, Class<? extends T> messageClass, Consumer<T> processor);

    void addMessageType(String type, ExceptionableFunction<JsonNode, Message> deserializer);
}
