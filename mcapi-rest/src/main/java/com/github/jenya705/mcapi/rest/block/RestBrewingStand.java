package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.BrewingStand;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
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
public class RestBrewingStand {

    private RestBlock block;
    private RestInventory inventory;
    private int fuelLevel;
    private int brewingTime;
    private UUID[] watchers;

    public static RestBrewingStand from(BrewingStand from) {
        return new RestBrewingStand(
                RestBlock.from(from.getBlock()),
                RestInventory.from(from.getInventory()),
                from.getFuelLevel(),
                from.getBrewingTime(),
                from
                        .getWatchers()
                        .stream()
                        .map(Player::getUuid)
                        .toArray(UUID[]::new)
        );
    }
}
