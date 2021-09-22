package com.github.jenya705.mcapi.module.config;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.MapConfigData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
public class ConfigModuleImpl extends AbstractApplicationModule implements ConfigModule {

    private ConfigData config;
    private GlobalConfig global;

    @OnInitializing(priority = 1)
    public void initialize() throws IOException {
        log.info("Loading config...");
        config = new MapConfigData(core().loadConfig("config"), app().getPlatform());
        log.info("Done! (Loading config...)");
        global = new GlobalConfig(config.required("global"));
    }

    @OnDisable(priority = 4)
    @OnStartup(priority = 4)
    public void save() throws IOException {
        core().saveConfig("config", config.primitiveRepresent());
    }

    @Override
    public GlobalConfig global() {
        return global;
    }
}
