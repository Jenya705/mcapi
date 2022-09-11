package dev.mcapi;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.mcapi.config.loader.ConfigLoader;
import dev.mcapi.inject.DirectoryPath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

public class ApplicationImpl implements Application {

    @RequiredArgsConstructor
    private static class SettingsInjectModule extends AbstractModule {
        private final ApplicationSettings settings;

        @Override
        protected void configure() {
            bind(Path.class)
                    .annotatedWith(DirectoryPath.class)
                    .toInstance(settings.directoryPath());
            bind(ConfigLoader.class).to(settings.configLoader());
            bind(java.util.logging.Logger.class).toInstance(settings.javaLogger());
            bind(org.slf4j.Logger.class).toInstance(settings.slf4jLogger());
        }
    }

    @Getter
    private final Injector injector;

    public ApplicationImpl(ApplicationSettings settings) {
        injector = Guice.createInjector(new SettingsInjectModule(settings));
    }

}
