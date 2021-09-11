package com.github.jenya705.mcapi.gateway.object;

import com.github.jenya705.mcapi.gateway.GatewayObject;
import com.github.jenya705.mcapi.link.LinkResponseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkResponseObject {

    @Data
    @AllArgsConstructor
    public static class ShortLinkResponse {
        private final boolean failed;
    }

    public static final String type = "link";

    public static GatewayObject<?> of(boolean isFailed, String[] declinePermissions) {
        return new GatewayObject<>(
                type,
                isFailed ?
                        new ShortLinkResponse(true) :
                        LinkResponseEntity.of(false, declinePermissions)
        );
    }
}
