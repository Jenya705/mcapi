package dev.mcapi.config.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.mcapi.inject.DirectoryPath;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

@Singleton
public class SnakeYamlConfigLoader implements ConfigLoader {

    private static final Yaml YAML = new Yaml();

    private final Path directoryPath;

    @Inject
    public SnakeYamlConfigLoader(@DirectoryPath Path directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    @SneakyThrows
    public Map<String, Object> loadConfig(String name) {
        File file = directoryPath.resolve(name + ".yml").toFile();
        if (!file.exists()) file = directoryPath.resolve(name + ".yaml").toFile();
        if (!file.exists()) return Collections.emptyMap();
        try (InputStream is = new FileInputStream(file)) {
            Map<String, Object> result = YAML.load(is);
            return result == null ? Collections.emptyMap() : result;
        }
    }
}
