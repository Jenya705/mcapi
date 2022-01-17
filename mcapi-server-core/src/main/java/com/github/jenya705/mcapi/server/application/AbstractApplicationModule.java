package com.github.jenya705.mcapi.server.application;

/**
 * @author Jenya705
 */
public abstract class AbstractApplicationModule implements BaseCommon {

    @Bean
    private ServerApplication application;

    public AbstractApplicationModule() {
    }

    public AbstractApplicationModule(ServerApplication application) {
        this.application = application;
    }

    @Override
    public ServerApplication app() {
        return application;
    }
}
