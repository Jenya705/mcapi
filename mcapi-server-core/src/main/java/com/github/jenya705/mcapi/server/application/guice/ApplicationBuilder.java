package com.github.jenya705.mcapi.server.application.guice;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;

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

    public ApplicationBuilder defaultLayers() {
        return layer(
                new ApplicationBuilderLayer()
                        .classes(ServerApplication.class)
        )
                .layer(ApplicationClassesBinder.ofModules().asLayer())
                .layer(ApplicationClassesBinder.ofRoutes().asLayer());
    }

    public ServerApplication build() {
        List<AbstractModule> orderedModules = new ArrayList<>();
        layers.forEach(it -> orderedModules.addAll(it.getModules()));
        Injector injector = Guice.createInjector(orderedModules);
        return injector.getProvider(ServerApplication.class).get();
    }

}
