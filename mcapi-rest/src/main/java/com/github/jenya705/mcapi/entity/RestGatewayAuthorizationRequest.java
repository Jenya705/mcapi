package com.github.jenya705.mcapi.entity;

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
public class RestGatewayAuthorizationRequest {

    private String token;

    public static RestGatewayAuthorizationRequest from(GatewayAuthorizationRequest request) {
        return new RestGatewayAuthorizationRequest(request.getToken());
    }
}
