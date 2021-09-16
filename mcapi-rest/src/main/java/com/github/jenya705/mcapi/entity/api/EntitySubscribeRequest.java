package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.ApiSubscribeRequest;
import com.github.jenya705.mcapi.entity.RestSubscribeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntitySubscribeRequest implements ApiSubscribeRequest {

    private String[] subscriptions;

    public RestSubscribeRequest rest() {
        return RestSubscribeRequest.from(this);
    }

}
