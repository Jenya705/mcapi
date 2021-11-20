package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.player.Player;
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
public class RestPlayer {

    private String name;
    private UUID uuid;
    private String type;
    private float yaw;
    private float pitch;
    private String gameMode;

    public static RestPlayer from(Player player) {
        return new RestPlayer(
                player.getName(),
                player.getUuid(),
                player.getType(),
                player.getYaw(),
                player.getPitch(),
                player.getGameMode().name()
        );
    }

}
