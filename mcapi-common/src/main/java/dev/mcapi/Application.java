package dev.mcapi;

import com.google.inject.Injector;

public interface Application {

    Injector getInjector();

    default <T> T getInstance(Class<T> clazz) {
        return getInjector().getInstance(clazz);
    }

}
