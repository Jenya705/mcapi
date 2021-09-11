package com.github.jenya705.mcapi.gateway.object;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.gateway.GatewayObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnlinkResponseObject {

    public static final String type = "unlink";

    private UUID player;

    public static GatewayObject<UnlinkResponseObject> of(ApiPlayer player) {
        return new GatewayObject<>(
                type,
                new UnlinkResponseObject(
                        player == null ? null : player.getUuid()
                )
        );
    }
}
