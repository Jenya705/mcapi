package com.github.jenya705.mcapi.module.web.tunnel;

import com.github.jenya705.mcapi.entity.AbstractBot;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface EventTunnelClient {

    AbstractBot getOwner();

    Collection<String> getSubscriptions();

    void authorization(String token);

    boolean subscribe(String subscription);

    boolean isSubscribed(String subscription);

    void send(Object obj);
}
