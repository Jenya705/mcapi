package com.github.jenya705.mcapi.server.ss.back;

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
import lombok.Getter;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.DisposableServer;
import reactor.netty.NettyOutbound;
import reactor.netty.tcp.TcpServer;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Jenya705
 */
@Singleton
public class BackServerModule extends AbstractApplicationModule {

    private final Collection<ProxyServerConnection> connections = new CopyOnWriteArrayList<>();
    private final DisposableServer server;
    private final Mapper mapper;
    private final EventLoop eventLoop;

    @Getter
    private final String password;

    @Inject
    public BackServerModule(ServerApplication application, Mapper mapper, EventLoop eventLoop) throws IOException {
        super(application);
        this.mapper = mapper;
        this.eventLoop = eventLoop;
        ConfigData data = new MapConfigData(core().loadConfig("back"));
        password = data.required("password", UUID.randomUUID().toString() + UUID.randomUUID());
        int port = data.required("port", 36543);
        server = TcpServer
                .create()
                .port(port)
                .doOnConnection(this::handleConnection)
                .bindNow();
    }

    public Publisher<Void> sendModel(NettyOutbound outbound, ProxyModel model) {
        return outbound.sendString(Mono.just(mapper.asJson(model)));
    }

    public Publisher<Void> sendModel(Connection connection, ProxyModel model) {
        return sendModel(connection.outbound(), model);
    }

    private void handleConnection(Connection connection) {
        ProxyServerConnection proxyServerConnection = new ProxyServerConnection(connection, this);
        connections.add(proxyServerConnection);
        connection
                .inbound()
                .receive()
                .asString()
                .map(json -> mapper.fromJson(json, ProxyModel.class))
                .filter(model -> proxyServerConnection.isAuthorized() || model.getType().equals(ProxyModels.AUTHENTICATION))
                .subscribe(model -> eventLoop.invoke(ProxyModelReceivedEvent.fromProxy(
                        model, new ProxyModelReceivedEvent.BackData(this, proxyServerConnection)
                )));
    }

}
