package com.github.jenya705.mcapi.server.ss.model.event;

import com.github.jenya705.mcapi.server.ss.back.BackEndServerModule;
import com.github.jenya705.mcapi.server.ss.model.ProxyModel;
import com.github.jenya705.mcapi.server.ss.proxy.ProxyServerModule;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @author Jenya705
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProxyModelReceivedEvent {

    public static ProxyModelReceivedEvent fromProxy(ProxyModel model, BackEndServerModule backEndServer) {
        return new ProxyModelReceivedEvent(model, backEndServer, null);
    }

    public static ProxyModelReceivedEvent fromBack(ProxyModel model, ProxyServerModule proxyServer) {
        return new ProxyModelReceivedEvent(model, null, proxyServer);
    }

    private final ProxyModel model;

    private final BackEndServerModule backEndServer;
    private final ProxyServerModule proxyServer;

    public Optional<BackEndServerModule> getBackEndServer() {
        return Optional.ofNullable(backEndServer);
    }

    public Optional<ProxyServerModule> getProxyServer() {
        return Optional.ofNullable(proxyServer);
    }

    public boolean fromBack() {
        return proxyServer != null;
    }

    public boolean fromProxy() {
        return backEndServer != null;
    }

}
