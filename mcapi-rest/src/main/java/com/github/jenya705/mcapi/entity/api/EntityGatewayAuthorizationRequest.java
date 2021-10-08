package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.GatewayAuthorizationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityGatewayAuthorizationRequest implements GatewayAuthorizationRequest {

    private String token;
}
