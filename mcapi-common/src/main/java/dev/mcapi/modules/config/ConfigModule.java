package dev.mcapi.modules.config;

import com.google.inject.ImplementedBy;

import java.nio.file.Path;

@ImplementedBy(ConfigModuleImpl.class)
public interface ConfigModule {

    ConfigData getConfig();

    ConfigData getConfig(Path path);

}
