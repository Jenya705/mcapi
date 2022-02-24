package com.github.jenya705.mcapi.server.module.web.tunnel;

import com.google.inject.ImplementedBy;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@ImplementedBy(DefaultEventTunnel.class)
public interface EventTunnel {

    void broadcast(Object obj, String type);

    void broadcast(Object obj, String type, Predicate<EventTunnelClient> predicate);

    Collection<? extends EventTunnelClient> getClients();

}
