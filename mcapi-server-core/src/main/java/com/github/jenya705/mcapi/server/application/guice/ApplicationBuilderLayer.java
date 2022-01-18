package com.github.jenya705.mcapi.server.application.guice;

import com.google.inject.AbstractModule;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ApplicationBuilderLayer {

    @Getter
    private final List<AbstractModule> modules = new ArrayList<>();

    public ApplicationBuilderLayer module(AbstractModule module) {
        modules.add(module);
        return this;
    }

    public ApplicationBuilderLayer classes(Class<?>... classes) {
        return module(ApplicationClassesBinder.of(classes));
    }

    public ApplicationBuilderLayer objects(Object... objects) {
        return module(new AbstractModule() {
            @Override
            @SuppressWarnings("unchecked")
            protected void configure() {
                for (Object obj: objects) {
                    bind((Class<Object>) obj.getClass()).toInstance(obj);
                }
            }
        });
    }

}
