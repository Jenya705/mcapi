package com.github.jenya705.mcapi.server.ss.back;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.MapConfigData;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.netty.Connection;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Jenya705
 */
@Singleton
public class BackEndServerModule extends AbstractApplicationModule {

    private final Collection<Connection> connections = new CopyOnWriteArrayList<>();
    private final DisposableServer server;

    @Inject
    public BackEndServerModule(ServerApplication application) throws IOException {
        super(application);
        ConfigData data = new MapConfigData(core().loadConfig("back"));
        String password = data.required("password", UUID.randomUUID().toString() + UUID.randomUUID());
        int port = data.required("port", 36543);
        server = TcpServer
                .create()
                .port(port)
                .bindNow();
    }

}
