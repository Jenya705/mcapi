package dev.mcapi.config;

import com.google.inject.ImplementedBy;

import java.nio.file.Path;

@ImplementedBy(ConfigContainerImpl.class)
public interface ConfigContainer {

    default ConfigData getDefaultData() {
        return getData("config");
    }

    ConfigData getData(String name);

    ConfigData loadData(String name);

    ConfigData getData(Path path);

    ConfigData loadData(Path path);

}
