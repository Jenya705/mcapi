package dev.mcapi.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.mcapi.Application;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ConfigContainerImpl implements ConfigContainer {

    private final Map<Path, ConfigData> loadedData = new ConcurrentHashMap<>();

    private final Path dataPath;
    private final Logger logger;
    private final ClassLoader loader;
    private final ConfigType configType;

    @Inject
    public ConfigContainerImpl(@Application.DataPath Path dataPath, Logger logger, ClassLoader loader, ConfigType configType) {
        this.dataPath = dataPath;
        this.logger = logger;
        this.loader = loader;
        this.configType = configType;
        dataPath.toFile().mkdirs();
    }

    @Override
    public ConfigData getData(String name) {
        return getData(dataPath.resolve(name + "." + configType.getFormat()), name);
    }

    @Override
    public ConfigData loadData(String name) {
        return loadData(dataPath.resolve(name + "." + configType.getFormat()), name);
    }

    @Override
    public ConfigData getData(Path path) {
        return getData(path, null);
    }

    private ConfigData getData(Path path, String resourceName) {
        return loadedData.computeIfAbsent(path, p -> loadData(path, resourceName));
    }

    @Override
    public ConfigData loadData(Path path) {
        return loadData(path, null);
    }

    @SneakyThrows
    private ConfigData loadData(Path path, String resourceName) {
        logger.info("Loading {}", path);
        Map<String, Object> result = new HashMap<>();
        if (resourceName != null) {
            try (InputStream resourceIs = loader.getResourceAsStream(resourceName + ".yml")) {
                if (resourceIs != null) {
                    result.putAll(ConfigType.YAML.getImpl().read(resourceIs));
                }
            }
        }
        File file = path.toFile();
        file.createNewFile();
        try (InputStream fileIs = new FileInputStream(file)) {
            result.putAll(configType.getImpl().read(fileIs));
        }
        try (OutputStream fileOs = new FileOutputStream(file)) {
            configType.getImpl().save(result, Collections.emptyMap(), fileOs);
        }
        return new ConfigDataImpl(Collections.unmodifiableMap(result));
    }

}
