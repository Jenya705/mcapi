package com.github.jenya705.mcapi.server.module.web.tunnel;

import com.github.jenya705.mcapi.server.entity.AbstractBot;

import java.util.Collection;
import java.util.Optional;

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

    void sendRaw(String str);

    default Optional<AbstractBot> getOptionalOwner() {
        return Optional.ofNullable(getOwner());
    }

}
