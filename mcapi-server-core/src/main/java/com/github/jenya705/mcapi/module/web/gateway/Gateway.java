package com.github.jenya705.mcapi.module.web.gateway;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface Gateway {

    void broadcast(Object obj, String type);

    Collection<? extends GatewayClient> getClients();
}
