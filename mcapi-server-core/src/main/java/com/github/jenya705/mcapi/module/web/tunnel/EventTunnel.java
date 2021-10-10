package com.github.jenya705.mcapi.module.web.tunnel;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface EventTunnel {

    void broadcast(Object obj, String type);

    Collection<? extends EventTunnelClient> getClients();
}
