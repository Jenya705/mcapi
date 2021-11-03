package com.github.jenya705.mcapi.entity;

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
public class EntityEventTunnelAuthorizationRequest implements EventTunnelAuthorizationRequest {

    private String token;
}
