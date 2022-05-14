package com.github.jenya705.mcapi.server.ss.back;

import com.github.jenya705.mcapi.server.ss.SSConnection;
import com.github.jenya705.mcapi.server.ss.model.ProxyModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import reactor.netty.Connection;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jenya705
 */
@Data
@Getter(AccessLevel.PRIVATE)
public class ProxyServerConnection implements SSConnection {

    private final AtomicBoolean authorized = new AtomicBoolean();
    private final Connection connection;
    private final BackServerModule backServer;

    public boolean isAuthorized() {
        return authorized.get();
    }

    public void authorize() {
        authorized.set(true);
    }

    public void sendModel(ProxyModel model) {
        backServer.sendModel(connection, model);
    }

}
