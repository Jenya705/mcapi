package com.github.jenya705.mcapi.data.loadable;

import com.github.jenya705.mcapi.ServerPlatform;
import com.github.jenya705.mcapi.data.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author Jenya705
 */
@Slf4j
public class CallbackLoadableConfigData extends GlobalConfigData implements LoadableConfigData {

    @Getter(AccessLevel.PROTECTED)
    private final BiFunction<Object, Class<?>, Object> deserializeFunction;

    public CallbackLoadableConfigData(Map<String, Object> data, Map<String, Object> globals, BiFunction<Object, Class<?>, Object> deserializeFunction) {
        super(data, globals);
        this.deserializeFunction = deserializeFunction;
    }

    public CallbackLoadableConfigData(Map<String, Object> data, Map<String, Object> globals, ServerPlatform platform, BiFunction<Object, Class<?>, Object> deserializeFunction) {
        super(data, globals, platform);
        this.deserializeFunction = deserializeFunction;
    }

    public CallbackLoadableConfigData(Map<String, Object> data, ServerPlatform platform, BiFunction<Object, Class<?>, Object> deserializeFunction) {
        super(data, platform);
        this.deserializeFunction = deserializeFunction;
    }

    public CallbackLoadableConfigData(Map<String, Object> data, BiFunction<Object, Class<?>, Object> deserializeFunction) {
        super(data);
        this.deserializeFunction = deserializeFunction;
    }

    public CallbackLoadableConfigData(BiFunction<Object, Class<?>, Object> deserializeFunction) {
        super();
        this.deserializeFunction = deserializeFunction;
    }

    @Override
    @SneakyThrows
    public void load(Object obj) {
        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            loadDependOnClass(obj, currentClass);
            currentClass = currentClass.getSuperclass();
        }
    }

    private void loadDependOnClass(Object object, Class<?> clazz) throws Exception {
        for (Field field : clazz.getDeclaredFields()) {
            Value valueAnnotation = field.getDeclaredAnnotation(Value.class);
            Global globalAnnotation = field.getAnnotation(Global.class);
            if (valueAnnotation == null) continue;
            field.setAccessible(true);
            String key = parseKey(valueAnnotation.key(), field.getName());
            try {
                Object fieldValue;
                ServerPlatform platform = getPlatform();
                if (platform == ServerPlatform.JAVA) {
                    Java javaAnnotation = field.getAnnotation(Java.class);
                    if (javaAnnotation == null) {
                        fieldValue = field.get(object);
                    }
                    else {
                        fieldValue = javaAnnotation.value();
                    }
                }
                else if (platform == ServerPlatform.BEDROCK) {
                    Bedrock bedrockAnnotation = field.getAnnotation(Bedrock.class);
                    if (bedrockAnnotation == null) {
                        fieldValue = field.get(object);
                    }
                    else {
                        fieldValue = bedrockAnnotation.value();
                    }
                }
                else {
                    fieldValue = field.get(object);
                }
                boolean isGlobal = false;
                if (globalAnnotation != null) {
                    Optional<Object> obj = getObject(key);
                    if (obj.isEmpty()) {
                        set(key, GlobalContainer.inheritKey);
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
                    Optional<Object> obj = global(globalAnnotation.value());
                    if (obj.isPresent()) {
                        field.set(object, obj.get());
                    }
                    else {
                        global(globalAnnotation.value(), fieldValue);
                    }
                }
                else if (valueAnnotation.required()) {
                    field.set(object, requiredObject(
                            key, fieldValue
                    ));
                }
                else {
                    Optional<Object> objectOptional = getObject(key);
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

    protected static ServerPlatform getPlatform(ConfigData data) {
        if (data instanceof PlatformContainer) {
            return ((PlatformContainer) data).getPlatform();
        }
        return ServerPlatform.OTHER;
    }

    protected static String parseKey(String key, String fieldName) {
        return key.isEmpty() ? fieldName : key;
    }

    @Override
    public MapConfigData createSelf(Map<String, Object> from) {
        return new CallbackLoadableConfigData(from, getGlobals(), getPlatform(), getDeserializeFunction());
    }
}
