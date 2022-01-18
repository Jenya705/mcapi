package com.github.jenya705.mcapi.server.module.web.tunnel;

import com.google.inject.ImplementedBy;

import java.util.Collection;

/**
 * @author Jenya705
 */
@ImplementedBy(DefaultEventTunnel.class)
public interface EventTunnel {

    void broadcast(Object obj, String type);

    Collection<? extends EventTunnelClient> getClients();
}
