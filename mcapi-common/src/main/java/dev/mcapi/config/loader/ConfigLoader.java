package dev.mcapi.config.loader;

import java.util.Map;

public interface ConfigLoader {

    Map<String, Object> loadConfig(String name);

}
