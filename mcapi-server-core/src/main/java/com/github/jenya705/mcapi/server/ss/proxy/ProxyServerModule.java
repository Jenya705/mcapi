package com.github.jenya705.mcapi.server.ss.proxy;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.MapConfigData;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jenya705
 */
@Singleton
public class ProxyServerModule extends AbstractApplicationModule {

    private final Map<String, Connection> backEndServers = new ConcurrentHashMap<>();

    @Inject
    @SuppressWarnings("unchecked")
    public ProxyServerModule(ServerApplication application) throws IOException {
        super(application);
        Objects.requireNonNullElse(core().loadConfig("proxy"), Collections.emptyMap())
                .forEach((name, dataMap) -> {
                    ConfigData data = new MapConfigData((Map<String, Object>) dataMap);
                    String hostWithIp = data.getString("host").orElseThrow();
                    String[] hostSplit = hostWithIp.split(":", 2);
                    String ip = hostSplit[0];
                    int port = Integer.parseInt(hostSplit[1]);
                    String password = data.getString("password").orElseThrow();
                    registerBackEndServer(String.valueOf(name), ip, port, password);
                });
    }

    public void registerBackEndServer(String name, String ip, int port, String password) {
        TcpClient
                .create()
                .host(ip)
                .port(port)
                .handle((nettyInbound, nettyOutbound) ->
                        nettyOutbound.sendString(Mono.just(password))
                )
                .connect()
                .subscribe(connection -> backEndServers.put(name, connection));
    }

    protected Connection getServerConnection(String name) {
        return backEndServers.get(name);
    }

}
