package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.SubscribeRequest;
import com.github.jenya705.mcapi.rest.RestSubscribeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntitySubscribeRequest implements SubscribeRequest {

    private String[] subscriptions;

    public RestSubscribeRequest rest() {
        return RestSubscribeRequest.from(this);
    }
}
