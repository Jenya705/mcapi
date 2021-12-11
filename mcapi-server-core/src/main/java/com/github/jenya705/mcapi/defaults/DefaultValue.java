package com.github.jenya705.mcapi.defaults;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

/**
 * @author Jenya705
 */
public interface DefaultValue<T> {

    static <T> DefaultValue<T> of(T defaultValue) {
        return new DefaultValueImpl<>(defaultValue);
    }

    T getDefaultValue();

    void write(JsonGenerator generator, T value) throws IOException;

}
