package com.github.jenya705.mcapi.server.module.mapper;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface RawDeserializer<T> {

    T deserialize(String value);
}
