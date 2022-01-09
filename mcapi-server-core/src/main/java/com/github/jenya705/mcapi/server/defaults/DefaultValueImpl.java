package com.github.jenya705.mcapi.server.defaults;

import com.fasterxml.jackson.core.JsonGenerator;
import com.github.jenya705.mcapi.server.util.Pair;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jenya705
 */
public class DefaultValueImpl<T> implements DefaultValue<T> {

    @Getter
    private final T defaultValue;
    private final List<Pair<Field, Object>> defaultFields;

    public DefaultValueImpl(T defaultValue) {
        this.defaultValue = defaultValue;
        defaultFields = new ArrayList<>();
        try {
            for (Field field : defaultValue.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                defaultFields.add(new Pair<>(field, field.get(defaultValue)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(JsonGenerator generator, T value) throws IOException {
        for (Pair<Field, Object> defaultField: defaultFields) {
            Object inValueObject;
            try {
                inValueObject = defaultField.getLeft().get(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (!Objects.equals(inValueObject, defaultField.getRight())) {
                generator.writeObjectField(defaultField.getLeft().getName(), inValueObject);
            }
        }
    }
}
