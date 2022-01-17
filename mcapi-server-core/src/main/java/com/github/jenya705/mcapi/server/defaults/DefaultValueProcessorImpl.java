package com.github.jenya705.mcapi.server.defaults;

import com.github.jenya705.mcapi.jackson.*;

import java.lang.reflect.Field;

public class DefaultValueProcessorImpl implements DefaultValueProcessor {

    @Override
    public Object getDefault(Field field) {
        DefaultNull defaultNull = field.getAnnotation(DefaultNull.class);
        if (defaultNull != null) {
            return null;
        }
        DefaultString defaultString = field.getAnnotation(DefaultString.class);
        if (defaultString != null) {
            return defaultString.value();
        }
        DefaultInteger defaultInteger = field.getAnnotation(DefaultInteger.class);
        if (defaultInteger != null) {
            return defaultInteger.value();
        }
        DefaultFloat defaultFloat = field.getAnnotation(DefaultFloat.class);
        if (defaultFloat != null) {
            return defaultFloat.value();
        }
        DefaultBoolean defaultBoolean = field.getAnnotation(DefaultBoolean.class);
        if (defaultBoolean != null) {
            return defaultBoolean.value();
        }
        return nothing;
    }
}
