package com.github.jenya705.mcapi.server.application.guice;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.*;

public class ApplicationBuilder {

    private final List<ApplicationBuilderLayer> layers = new ArrayList<>();

    public ApplicationBuilder layer(ApplicationBuilderLayer layer) {
        layers.add(layer);
        return this;
    }

    public ApplicationBuilder layer(AbstractModule abstractModule) {
        return layer(new ApplicationBuilderLayer().module(abstractModule));
    }

    public ApplicationBuilder layer(Class<?>... classes) {
        return layer(ApplicationClassesBinder.of(classes));
    }

    public ApplicationBuilder layer(Collection<Class<?>> classes) {
        return layer(classes, Collections.emptyList());
    }

    public ApplicationBuilder layer(Collection<Class<?>> classes, Collection<Class<?>> except) {
        return layer(new ApplicationClassesBinder(classes, except));
    }

    public ApplicationBuilder defaultLayers(Class<?>... except) {
        List<Class<?>> exceptList = Arrays.asList(except);
        return layer(new ApplicationLoggerBinder())
                .layer(ServerApplication.class)
                .layer(new ApplicationClassesBinder(ApplicationClassesBinder.modules, exceptList))
                .layer(new ApplicationClassesBinder(ApplicationClassesBinder.routes, exceptList))
                .layer(new ApplicationClassesBinder(ApplicationClassesBinder.commands, exceptList));
    }

    public ServerApplication build() {
        List<AbstractModule> orderedModules = new ArrayList<>();
        layers.forEach(it -> orderedModules.addAll(it.getModules()));
        Injector injector = Guice.createInjector(orderedModules);
        return injector.getProvider(ServerApplication.class).get();
    }

}
