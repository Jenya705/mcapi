package com.github.jenya705.mcapi.server.ss.model.event;

import com.github.jenya705.mcapi.server.ss.back.BackServerModule;
import com.github.jenya705.mcapi.server.ss.back.ProxyServerConnection;
import com.github.jenya705.mcapi.server.ss.model.ProxyModel;
import com.github.jenya705.mcapi.server.ss.proxy.BackServerConnection;
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

    @Data
    public static class ProxyData {
        private final ProxyServerModule module;
        private final BackServerConnection connection;
    }

    @Data
    public static class BackData {
        private final BackServerModule module;
        private final ProxyServerConnection connection;
    }

    public static ProxyModelReceivedEvent fromProxy(ProxyModel model, BackData backData) {
        return new ProxyModelReceivedEvent(model, backData, null);
    }

    public static ProxyModelReceivedEvent fromBack(ProxyModel model, ProxyData proxyData) {
        return new ProxyModelReceivedEvent(model, null, proxyData);
    }

    private final ProxyModel model;

    private final BackData back;
    private final ProxyData proxy;

    public Optional<BackData> getBack() {
        return Optional.ofNullable(back);
    }

    public Optional<ProxyData> getProxy() {
        return Optional.ofNullable(proxy);
    }

    public boolean fromBack() {
        return proxy != null;
    }

    public boolean fromProxy() {
        return back != null;
    }

}
