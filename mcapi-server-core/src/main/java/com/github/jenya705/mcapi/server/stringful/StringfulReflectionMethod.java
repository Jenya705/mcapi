package com.github.jenya705.mcapi.server.stringful;

import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Jenya705
 */
public class StringfulReflectionMethod implements StringfulReflectionObject {

    private final Method method;

    public StringfulReflectionMethod(Method method) {
        this.method = method;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotation) {
        return method.getAnnotation(annotation);
    }

    @Override
    public void setAccessible(boolean accessible) {
        method.setAccessible(accessible);
    }

    @Override
    @SneakyThrows
    public void invoke(Object data, Object value) {
        if (method.getParameterTypes().length != 1) {
            throw new IllegalArgumentException("Method parameter types length is not 1");
        }
        method.invoke(data, value);
    }

    @Override
    public Class<?> getType() {
        return method.getParameterTypes()[0];
    }
}
