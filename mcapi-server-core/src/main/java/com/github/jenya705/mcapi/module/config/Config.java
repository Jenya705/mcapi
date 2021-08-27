package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.data.ConfigData;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Jenya705
 */
@Slf4j
public class Config {

    public void load(ConfigData data) {
        Class<?> current = getClass();
        while (current != null) {
            setFields(this, current, data);
            current = current.getSuperclass();
        }
    }

    public static <T> void setFields(T object, Class<? extends T> clazz, ConfigData data) {
        for (Field field : clazz.getDeclaredFields()) {
            Value valueAnnotation = field.getDeclaredAnnotation(Value.class);
            if (valueAnnotation == null) continue;
            field.setAccessible(true);
            try {
                if (valueAnnotation.required()) {
                    field.set(object, data.requiredObject(
                            parseKey(valueAnnotation.key(), field.getName()),
                            field.get(object)
                    ));
                }
                else {
                    Optional<Object> objectOptional = data.getObject(
                            parseKey(valueAnnotation.key(), field.getName())
                    );
                    if (objectOptional.isPresent()) field.set(object, objectOptional.get());
                }
            } catch (IllegalAccessException e) {
                log.error(
                        String.format(
                                "IllegalAccessException in %s with field %s",
                                clazz.getCanonicalName(),
                                field.getName()
                        ), e
                );
            }
            field.setAccessible(false);
        }
    }

    protected static String parseKey(String key, String fieldName) {
        return key.isEmpty() ? fieldName : key;
    }

}
