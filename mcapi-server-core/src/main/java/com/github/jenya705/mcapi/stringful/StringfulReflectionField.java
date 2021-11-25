package com.github.jenya705.mcapi.stringful;

import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Jenya705
 */
public class StringfulReflectionField implements StringfulReflectionObject {

    private final Field field;

    public StringfulReflectionField(Field field) {
        this.field = field;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotation) {
        return field.getAnnotation(annotation);
    }

    @Override
    public void setAccessible(boolean accessible) {
        if (Modifier.isPublic(field.getModifiers())) return;
        field.setAccessible(accessible);
    }

    @Override
    @SneakyThrows
    public void invoke(Object data, Object value) {
        field.set(data, value);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
