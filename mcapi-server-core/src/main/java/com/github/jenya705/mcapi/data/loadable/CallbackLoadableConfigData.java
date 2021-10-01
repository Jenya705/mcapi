package com.github.jenya705.mcapi.data.loadable;

import com.github.jenya705.mcapi.ServerPlatform;
import com.github.jenya705.mcapi.data.GlobalConfigData;
import com.github.jenya705.mcapi.data.GlobalContainer;
import com.github.jenya705.mcapi.data.MapConfigData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Jenya705
 */
@Slf4j
public class CallbackLoadableConfigData extends GlobalConfigData implements LoadableConfigData {

    @Getter(AccessLevel.PROTECTED)
    private final CallbackLoadableConfigDataSerializer serializer;

    public CallbackLoadableConfigData(Map<String, Object> data, Map<String, Object> globals, CallbackLoadableConfigDataSerializer serializer) {
        super(data, globals);
        this.serializer = serializer;
    }

    public CallbackLoadableConfigData(Map<String, Object> data, Map<String, Object> globals, ServerPlatform platform, CallbackLoadableConfigDataSerializer serializer) {
        super(data, globals, platform);
        this.serializer = serializer;
    }

    public CallbackLoadableConfigData(Map<String, Object> data, ServerPlatform platform, CallbackLoadableConfigDataSerializer serializer) {
        super(data, platform);
        this.serializer = serializer;
    }

    public CallbackLoadableConfigData(Map<String, Object> data, CallbackLoadableConfigDataSerializer serializer) {
        super(data);
        this.serializer = serializer;
    }

    public CallbackLoadableConfigData(CallbackLoadableConfigDataSerializer serializer) {
        super();
        this.serializer = serializer;
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
            Value value = field.getAnnotation(Value.class);
            if (value == null) continue;
            Global global = field.getAnnotation(Global.class);
            String name = value.key().isEmpty() ? field.getName() : value.key();
            Object endValue = getObject(name).orElse(null);
            field.setAccessible(true);
            if (global != null && GlobalContainer.inheritKey.equals(endValue)) {
                // global value used
                endValue = global(global.value()).orElse(null);
            }
            if (endValue == null) {
                // not assigned
                if (!value.required()) continue; // not required to assign
                Object fieldValue;
                Java javaValue = field.getAnnotation(Java.class);
                Bedrock bedrockValue = field.getAnnotation(Bedrock.class);
                if (getPlatform() == ServerPlatform.JAVA && javaValue != null) {
                    fieldValue = javaValue.value();
                }
                else if (getPlatform() == ServerPlatform.BEDROCK && bedrockValue != null) {
                    fieldValue = bedrockValue.value();
                }
                else {
                    fieldValue = field.get(object);
                }
                if (global == null) {
                    set(name, fieldValue);
                }
                else {
                    global(global.value(), serializer.serialize(fieldValue));
                    set(name, GlobalContainer.inheritKey);
                }
                endValue = serializer.deserialize(fieldValue, field.getType()); // specific platform need to be set
            }
            field.set(object, serializer.deserialize(endValue, field.getType()));
        }
    }

    @Override
    public MapConfigData createSelf(Map<String, Object> from) {
        return new CallbackLoadableConfigData(from, getGlobals(), getPlatform(), getSerializer());
    }
}
