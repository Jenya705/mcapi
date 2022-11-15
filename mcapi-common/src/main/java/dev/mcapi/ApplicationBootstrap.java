package dev.mcapi;


import dev.mcapi.config.ConfigType;
import lombok.Data;
import org.slf4j.Logger;

import java.nio.file.Path;

@Data
public class ApplicationBootstrap {

    private final Logger logger;
    private final ClassLoader classLoader;
    private final Path dataPath;
    private final ConfigType configType;

}
