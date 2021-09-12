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
public class GatewayJoinEvent {

    public static final String type = "join";

    private UUID player;

    public static GatewayObject<GatewayJoinEvent> of(ApiPlayer player) {
        return new GatewayObject<>(
                type,
                new GatewayJoinEvent(
                        player.getUuid()
                )
        );
    }

}
