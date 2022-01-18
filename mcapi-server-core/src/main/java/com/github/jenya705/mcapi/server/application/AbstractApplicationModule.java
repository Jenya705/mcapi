package com.github.jenya705.mcapi.server.application;

/**
 * @author Jenya705
 */
public abstract class AbstractApplicationModule implements BaseCommon {

    private final ServerApplication application;

    public AbstractApplicationModule(ServerApplication application) {
        this.application = application;
    }

    @Override
    public ServerApplication app() {
        return application;
    }
}
