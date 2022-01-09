package com.github.jenya705.mcapi.server.stringful;

import java.lang.annotation.Annotation;

/**
 * @author Jenya705
 */
public interface StringfulReflectionObject {

    <T extends Annotation> T getAnnotation(Class<T> annotation);

    void setAccessible(boolean accessible);

    void invoke(Object data, Object value);

    Class<?> getType();

}
