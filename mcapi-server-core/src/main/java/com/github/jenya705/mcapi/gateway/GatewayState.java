package com.github.jenya705.mcapi.gateway;

import com.github.jenya705.mcapi.JacksonProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public enum GatewayState {

    LISTENING(
            (client, jsonData) -> {
                GatewaySubscribeRequest request = JacksonProvider
                        .getMapper()
                        .readValue(jsonData, GatewaySubscribeRequest.class);
                List<String> failed = new ArrayList<>();
                for (String subscription : request.getSubscriptions()) {
                    if (!client.subscribe(subscription)) failed.add(subscription);
                }
                GatewaySubscribeResponse response = new GatewaySubscribeResponse(
                        failed.toArray(new String[0])
                );
                client.send(response);
            }
    ),
    AUTHORIZATION(
            (client, jsonData) -> {
                GatewayLoginRequest request = JacksonProvider
                        .getMapper()
                        .readValue(jsonData, GatewayLoginRequest.class);
                client.authorization(request.getToken());
                List<String> failed = new ArrayList<>();
                for (String subscription : request.getSubscriptions()) {
                    if (!client.subscribe(subscription)) failed.add(subscription);
                }
                GatewayLoginResponse response = new GatewayLoginResponse(
                        failed.toArray(new String[0])
                );
                client.send(response);
                client.setCurrentState(GatewayState.LISTENING);
            }
    );

    @FunctionalInterface
    interface GatewayStateConsumer {
        void accept(GatewayClientImpl client, String jsonData) throws Exception;
    }

    private final GatewayStateConsumer consumer;
}
