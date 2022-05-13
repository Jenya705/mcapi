package com.github.jenya705.mcapi.server.ss.proxy;

import com.github.jenya705.mcapi.server.ss.model.ProxyModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import reactor.netty.Connection;

/**
 * @author Jenya705
 */
@Data
public class BackEndServerConnection {

    private final String name;
    @Getter(AccessLevel.PRIVATE)
    private final Connection connection;
    private final ProxyServerModule proxyServer;

    public void sendModel(ProxyModel model) {
        proxyServer.sendModel(connection, model);
    }

    public void sendModel(String type, Object obj) {
        sendModel(new ProxyModel(type, obj));
    }

}
