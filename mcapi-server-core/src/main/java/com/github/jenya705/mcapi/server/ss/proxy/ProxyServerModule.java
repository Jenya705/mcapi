package com.github.jenya705.mcapi.server.ss.proxy;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.netty.tcp.TcpClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jenya705
 */
@Singleton
public class ProxyServerModule extends AbstractApplicationModule {

    private final Map<String, TcpClient> backEndServers = new ConcurrentHashMap<>();

    @Inject
    public ProxyServerModule(ServerApplication application) {
        super(application);
    }

}
