package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.gateway.GatewayObject;
import com.github.jenya705.mcapi.ApiLinkResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayLinkEvent implements ApiLinkResponse {

    @Data
    @AllArgsConstructor
    public static class ShortLinkResponse {
        private final boolean failed;
    }

    private boolean failed;
    private String[] declinePermissions = {};

    public static final String type = "link";

    public static GatewayObject<?> of(boolean isFailed, String[] declinePermissions) {
        return new GatewayObject<>(
                type,
                isFailed ?
                        new ShortLinkResponse(true) :
                        new GatewayLinkEvent(false, declinePermissions)
        );
    }
}
