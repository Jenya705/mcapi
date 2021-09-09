package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.ServerPlatform;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.GlobalContainer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
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
            Global globalAnnotation = null;
            if (data instanceof GlobalContainer) {
                globalAnnotation = field.getAnnotation(Global.class);
            }
            if (valueAnnotation == null) continue;
            field.setAccessible(true);
            String key = parseKey(valueAnnotation.key(), field.getName());
            try {
                Object fieldValue;
                if (ServerApplication.getApplication().getPlatform() == ServerPlatform.JAVA) {
                    Java javaAnnotation = field.getAnnotation(Java.class);
                    if (javaAnnotation == null) {
                        fieldValue = field.get(object);
                    }
                    else {
                        fieldValue = javaAnnotation.value();
                        field.set(object, fieldValue);
                    }
                }
                else {
                    fieldValue = field.get(object);
                }
                boolean isGlobal = false;
                if (globalAnnotation != null) {
                    Optional<Object> obj = data.getObject(key);
                    if (obj.isEmpty()) {
                        data.set(key, GlobalContainer.inheritKey);
                        isGlobal = true;
                    }
                    else {
                        isGlobal = obj
                                .filter(it -> it instanceof String)
                                .map(it -> it.equals(GlobalContainer.inheritKey))
                                .orElse(false);
                    }
                }
                if (isGlobal) {
                    GlobalContainer globalContainer = (GlobalContainer) data;
                    Optional<Object> obj = globalContainer.global(globalAnnotation.value());
                    if (obj.isPresent()) {
                        field.set(object, obj.get());
                    }
                    else {
                        globalContainer.global(globalAnnotation.value(), fieldValue);
                    }
                }
                else if (valueAnnotation.required()) {
                    field.set(object, data.requiredObject(
                            key, fieldValue
                    ));
                }
                else {
                    Optional<Object> objectOptional = data.getObject(key);
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
