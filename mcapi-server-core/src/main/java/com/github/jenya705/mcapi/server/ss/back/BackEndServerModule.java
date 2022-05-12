package com.github.jenya705.mcapi.server.ss.back;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.netty.tcp.TcpServer;

/**
 * @author Jenya705
 */
@Singleton
public class BackEndServerModule extends AbstractApplicationModule {

    private final TcpServer server;

    @Inject
    public BackEndServerModule(ServerApplication application) {
        super(application);
        server = null;
    }

}
