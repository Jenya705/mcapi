package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.gateway.GatewayObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayUnlinkEvent {

    public static final String type = "unlink";

    private UUID player;

    public static GatewayObject<GatewayUnlinkEvent> of(ApiPlayer player) {
        return new GatewayObject<>(
                type,
                new GatewayUnlinkEvent(
                        player == null ? null : player.getUuid()
                )
        );
    }
}
