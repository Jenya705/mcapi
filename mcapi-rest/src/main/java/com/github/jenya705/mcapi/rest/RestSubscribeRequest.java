package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.SubscribeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestSubscribeRequest {

    private String[] subscriptions;

    public static RestSubscribeRequest from(SubscribeRequest request) {
        return new RestSubscribeRequest(
                request.getSubscriptions()
        );
    }
}
