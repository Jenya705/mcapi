package com.github.jenya705.mcapi.server.defaults;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public interface DefaultValueProcessor {

    Object nothing = new Object();

    Object getDefault(Field field);

    default boolean isDefault(Object value, Field field) {
        Object defaultValue = getDefault(field);
        if (defaultValue == null) {
            if (value instanceof Optional) {
                return ((Optional<?>) value).isEmpty();
            }
            if (value instanceof Collection) {
                return ((Collection<?>) value).isEmpty();
            }
            if (value instanceof Object[]) {
                return ((Object[]) value).length == 0;
            }
        }
        return Objects.equals(value, defaultValue);
    }

    default void writeIfNotDefault(JsonGenerator generator, Object value, Field field, boolean raw) throws IOException {
        writeIfNotDefault(generator, value, field.getName(), field, raw);
    }

    default void writeIfNotDefault(JsonGenerator generator, Object value, String key, Field field, boolean raw) throws IOException {
        if (!isDefault(value, field)) {
            generator.writeFieldName(key);
            if (raw) {
                String rawValue = value instanceof String ? "\"" + value + "\"" : String.valueOf(value);
                generator.writeRawValue(rawValue);
            }
            else {
                generator.writeObject(value);
            }
        }
    }

}
