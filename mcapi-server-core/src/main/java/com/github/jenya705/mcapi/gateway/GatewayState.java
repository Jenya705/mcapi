package com.github.jenya705.mcapi.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jenya705.mcapi.JacksonProvider;
import com.github.jenya705.mcapi.entity.api.EntitySubscribeRequest;
import com.github.jenya705.mcapi.entity.api.event.EntitySubscribeEvent;
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
            GatewayState::subscribe
    ),
    AUTHORIZATION(
            (client, jsonData) -> {
                subscribe(client, jsonData);
                client.setCurrentState(GatewayState.LISTENING);
            }
    );

    private static void subscribe(GatewayClientImpl client, String jsonData) throws JsonProcessingException {
        EntitySubscribeRequest request = JacksonProvider
                .getMapper()
                .readValue(jsonData, EntitySubscribeRequest.class);
        List<String> failed = new ArrayList<>();
        for (String subscription : request.getSubscriptions()) {
            if (!client.subscribe(subscription)) failed.add(subscription);
        }
        client.send(
                new EntitySubscribeEvent(
                        failed.toArray(String[]::new)
                ).rest()
        );
    }

    @FunctionalInterface
    interface GatewayStateConsumer {
        void accept(GatewayClientImpl client, String jsonData) throws Exception;
    }

    private final GatewayStateConsumer consumer;
}
