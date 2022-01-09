package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.RestLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

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
    private RestLocation location;
    private String gameMode;
    private RestPlayerAbilities abilities;
    private Component customName;
    private float health;
    private boolean ai;

    public static RestPlayer from(Player player) {
        return new RestPlayer(
                player.getName(),
                player.getUuid(),
                player.getType(),
                RestLocation.from(player.getLocation()),
                player.getGameMode().name(),
                RestPlayerAbilities.from(player.getAbilities()),
                player.customName(),
                player.getHealth(),
                player.hasAI()
        );
    }

}
