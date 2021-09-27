package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiGatewayAuthorizationRequest;
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

    public static RestGatewayAuthorizationRequest from(ApiGatewayAuthorizationRequest request) {
        return new RestGatewayAuthorizationRequest(request.getToken());
    }
}
