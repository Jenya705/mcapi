package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
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
public class GatewayQuitEvent {

    public static final String type = "quit";

    private UUID player;

    public static GatewayObject<GatewayQuitEvent> of(ApiOfflinePlayer player) {
        return new GatewayObject<>(
                type,
                new GatewayQuitEvent(
                        player.getUuid()
                )
        );
    }

}
