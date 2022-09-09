package dev.mcapi.config;

public interface ConfigData {

    <T> T get(String key);

    ConfigData getData(String key);

}
