package com.github.jenya705.mcapi.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayLoginRequest {

    private String token;
    private String[] subscriptions;
}
