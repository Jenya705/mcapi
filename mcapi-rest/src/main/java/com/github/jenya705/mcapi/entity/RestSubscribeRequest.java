package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiSubscribeRequest;
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

    public static RestSubscribeRequest from(ApiSubscribeRequest request) {
        return new RestSubscribeRequest(
                request.getSubscriptions()
        );
    }

}
