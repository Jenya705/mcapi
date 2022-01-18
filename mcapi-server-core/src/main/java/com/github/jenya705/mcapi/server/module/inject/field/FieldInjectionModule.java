package com.github.jenya705.mcapi.server.module.inject.field;

import com.google.inject.ImplementedBy;

import java.util.Set;
import java.util.function.Function;

@ImplementedBy(FieldInjectionModuleImpl.class)
public interface FieldInjectionModule {

    Set<String> getIgnorableFields(Class<?> clazz);

    void registerClass(Class<?> clazz);

    <T> void registerField(Class<T> clazz, String field, Function<T, Object> getter);

    default void registerClasses(Class<?>... classes) {
        for (Class<?> clazz: classes) registerClass(clazz);
    }

}
