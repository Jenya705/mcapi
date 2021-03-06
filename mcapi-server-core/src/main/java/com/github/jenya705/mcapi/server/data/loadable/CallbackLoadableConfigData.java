package com.github.jenya705.mcapi.server.data.loadable;

import com.github.jenya705.mcapi.server.data.GlobalConfigData;
import com.github.jenya705.mcapi.server.data.GlobalContainer;
import com.github.jenya705.mcapi.server.data.MapConfigData;
import lombok.AccessLevel;
import lombok.Getter;
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

    public CallbackLoadableConfigData(Map<String, Object> data, CallbackLoadableConfigDataSerializer serializer) {
        super(data);
        this.serializer = serializer;
    }

    public CallbackLoadableConfigData(CallbackLoadableConfigDataSerializer serializer) {
        super();
        this.serializer = serializer;
    }

    @Override
    public void load(Object obj) {
        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            loadDependOnClass(obj, currentClass);
            currentClass = currentClass.getSuperclass();
        }
    }

    private void loadDependOnClass(Object object, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                Value value = field.getAnnotation(Value.class);
                if (value == null) continue;
                Global global = field.getAnnotation(Global.class);
                String name = value.key().isEmpty() ? field.getName() : value.key();
                Object endValue = getObject(name).orElse(null);
                field.setAccessible(true);
                if (global != null &&
                        endValue instanceof String &&
                        ((String) endValue).startsWith(GlobalContainer.inheritKey)) {
                    // global value used
                    endValue = global(global.value()).orElse(null);
                }
                if (endValue == null) {
                    // not assigned
                    if (!value.required()) continue; // not required to assign
                    Object fieldValue = field.get(object);
                    if (global == null) {
                        set(name, serializer.serialize(fieldValue, this, name));
                    }
                    else {
                        global(global.value(), serializer.serialize(fieldValue, this, name));
                        set(name, GlobalContainer.inheritKey + global.value());
                    }
                    endValue = fieldValue; // specific platform need to be set
                }
                field.set(object, serializer.deserialize(endValue, field.getType(), this, name));
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format(
                                "Exception in %s field in %s class",
                                field.getName(),
                                clazz.getCanonicalName()
                        ), e
                );
            }
        }
    }

    @Override
    public MapConfigData createSelf(Map<String, Object> from) {
        return new CallbackLoadableConfigData(from, getGlobals(), getSerializer());
    }
}
