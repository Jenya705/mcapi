package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.web.WebModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
public class ServerApplication {

    @Getter
    private static ServerApplication application;

    private final WebModule webModule = new WebModule(this);
    private final ServerCore core;

    public ServerApplication(ServerCore core) {
        if (application == null) {
            throw new IllegalStateException("Can not instantiate object twice");
        }
        application = this;
        this.core = core;
    }

    public void start() {
        forEachModule(ServerModule::load, "loading");
        forEachModule(ServerModule::start, "starting");
    }

    public void stop() {
        forEachModule(ServerModule::stop, "stopping");
    }

    public Map<String, ServerModule> getModules() {
        return Map.of(
            "web", getWebModule()
        );
    }

    protected void forEachModule(Consumer<ServerModule> consumer, String operationName) {
        getModules().forEach((name, module) -> {
            log.info(String.format("%s %s module", ServerUtils.highFirstLetter(operationName), name));
            try {
                consumer.accept(module);
            } catch (Exception e) {
                log.error(String.format("Error while %s %s module", operationName, name), e);
            }
        });
    }


}
