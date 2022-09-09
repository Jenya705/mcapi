package dev.mcapi.config;

public interface ConfigStorage {

    default ConfigData getGlobalConfig() {
        return getConfig("config");
    }

    ConfigData getConfig(String name);

}
