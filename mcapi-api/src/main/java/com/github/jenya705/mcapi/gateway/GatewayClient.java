package com.github.jenya705.mcapi.gateway;

/**
 * @author Jenya705
 */
public interface GatewayClient {

    void authorization(String token);

    boolean subscribe(String subscription);

    boolean isSubscribed(String subscription);
}
