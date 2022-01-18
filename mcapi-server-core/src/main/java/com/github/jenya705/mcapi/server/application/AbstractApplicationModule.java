package com.github.jenya705.mcapi.server.application;

import com.google.inject.Provider;
import com.google.inject.util.Providers;

/**
 * @author Jenya705
 */
public abstract class AbstractApplicationModule implements BaseCommon {

    private final Provider<ServerApplication> application;

    public AbstractApplicationModule(ServerApplication application) {
        this(Providers.of(application));
    }

    public AbstractApplicationModule(Provider<ServerApplication> provider) {
        this.application = provider;
    }

    @Override
    public ServerApplication app() {
        return application.get();
    }

    public Provider<ServerApplication> appProvider() {
        return application;
    }
}
