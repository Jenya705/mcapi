package dev.mcapi;

import dev.mcapi.config.loader.ConfigLoader;
import org.immutables.value.Value;

import java.nio.file.Path;

@Value.Immutable
public interface ApplicationSettings {

    static ImmutableApplicationSettings.Builder builder() {
        return ImmutableApplicationSettings.builder();
    }

    Path directoryPath();

    Class<? extends ConfigLoader> configLoader();

    org.slf4j.Logger slf4jLogger();

    java.util.logging.Logger javaLogger();

}
