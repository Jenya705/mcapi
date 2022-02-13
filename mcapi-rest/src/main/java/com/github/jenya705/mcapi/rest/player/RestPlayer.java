package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.jackson.DefaultBoolean;
import com.github.jenya705.mcapi.jackson.DefaultFloat;
import com.github.jenya705.mcapi.jackson.DefaultNull;
import com.github.jenya705.mcapi.jackson.DefaultString;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.RestLocation;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
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
    @DefaultNull
    private RestLocation location;
    @DefaultString("SURVIVAL")
    private String gameMode;
    private RestPlayerAbilities abilities;
    @DefaultNull
    private Component customName;
    @DefaultFloat(20)
    private float health;
    @DefaultBoolean(false)
    private boolean ai;
    private RestInventory inventory;

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
                player.hasAI(),
                RestInventory.from(player.getInventory())
        );
    }

}
