package com.github.jenya705.mcapi.server.ss.proxy;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.MapConfigData;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.ss.model.ProxyModel;
import com.github.jenya705.mcapi.server.ss.model.ProxyModels;
import com.github.jenya705.mcapi.server.ss.model.event.ProxyModelReceivedEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyOutbound;
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

    private final Map<String, BackEndServerConnection> backEndServers = new ConcurrentHashMap<>();
    private final Mapper mapper;
    private final EventLoop eventLoop;

    @Inject
    @SuppressWarnings("unchecked")
    public ProxyServerModule(ServerApplication application, Mapper mapper, EventLoop eventLoop) throws IOException {
        super(application);
        this.mapper = mapper;
        this.eventLoop = eventLoop;
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

    public Publisher<Void> sendModel(NettyOutbound outbound, ProxyModel model) {
        return outbound.sendString(Mono.just(mapper.asJson(model)));
    }

    public Publisher<Void> sendModel(Connection connection, ProxyModel model) {
        return sendModel(connection.outbound(), model);
    }

    public void registerBackEndServer(String name, String ip, int port, String password) {
        TcpClient
                .create()
                .host(ip)
                .port(port)
                .handle((nettyInbound, nettyOutbound) ->
                        sendModel(
                                nettyOutbound,
                                new ProxyModel(ProxyModels.AUTHENTICATION, password)
                        )
                )
                .connect()
                .doOnNext(connection -> backEndServers.put(
                        name,
                        new BackEndServerConnection(
                                name, connection, this
                        )
                ))
                .doOnNext(connection -> connection
                        .inbound()
                        .receive()
                        .asString()
                        .map(json -> mapper.fromJson(json, ProxyModel.class))
                        .subscribe(model -> eventLoop.invoke(
                                ProxyModelReceivedEvent.fromBack(model, this)
                        ))
                )
                .subscribe();
    }

    public BackEndServerConnection getServerConnection(String name) {
        return backEndServers.get(name);
    }

}
