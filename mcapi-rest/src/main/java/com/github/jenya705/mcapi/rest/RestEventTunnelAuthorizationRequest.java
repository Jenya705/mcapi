package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.EventTunnelAuthorizationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEventTunnelAuthorizationRequest {

    private String token;

    public static RestEventTunnelAuthorizationRequest from(EventTunnelAuthorizationRequest request) {
        return new RestEventTunnelAuthorizationRequest(request.getToken());
    }
}
