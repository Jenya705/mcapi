package dev.mcapi.modules.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.mcapi.ApplicationBoostrap;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class ConfigModuleImpl implements ConfigModule {

    private static final Yaml YAML = new Yaml();

    private final Map<Path, ConfigData> configs = new ConcurrentHashMap<>();

    private final ApplicationBoostrap boostrap;

    @Override
    public ConfigData getConfig() {
        return getConfig(Path.of("config.yml"));
    }

    @Override
    public ConfigData getConfig(Path path) {
        return configs.computeIfAbsent(path, this::loadConfig);
    }

    @SneakyThrows
    private ConfigData loadConfig(Path path) {
        Path configPath = boostrap.getPath().resolve(path);
        try (InputStream is = new FileInputStream(configPath.toFile())) {
            Map<String, Object> map = YAML.load(is);
            return ConfigData.fromMap(map);
        }
    }

}
