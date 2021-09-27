package com.github.jenya705.mcapi.module.mapper;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface RawDeserializer<T> {

    T deserialize(String value);
}
