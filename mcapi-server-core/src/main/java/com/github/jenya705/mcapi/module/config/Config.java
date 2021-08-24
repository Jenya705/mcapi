package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.data.ConfigData;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author Jenya705
 */
@Slf4j
public class Config {

    public Config(ConfigData data) {
        for (Field field : getClass().getDeclaredFields()) {
            Value valueAnnotation = field.getDeclaredAnnotation(Value.class);
            if (valueAnnotation == null) continue;
            field.setAccessible(true);
            try {
                if (valueAnnotation.required()) {
                    field.set(this, data.requiredObject(
                            parseKey(valueAnnotation.key(), field.getName()),
                            field.get(this)
                    ));
                }
                else {
                    Optional<Object> objectOptional = data.getObject(
                            parseKey(valueAnnotation.key(), field.getName())
                    );
                    if (objectOptional.isPresent()) field.set(this, objectOptional.get());
                }
            } catch (IllegalAccessException e) {
               log.error(
                       String.format(
                               "IllegalAccessException in %s with field %s",
                               getClass().getCanonicalName(),
                               field.getName()
                       ), e
               );
            }
            field.setAccessible(false);
        }
    }

    protected String parseKey(String key, String fieldName) {
        return key.isEmpty() ? fieldName : key;
    }

}
