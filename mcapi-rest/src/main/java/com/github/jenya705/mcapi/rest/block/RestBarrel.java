package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.Barrel;
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
public class RestBarrel {

    private RestBlock block;
    private UUID[] watchers;
    private RestInventory inventory;
    private String direction;

    public static RestBarrel from(Barrel barrel) {
        return new RestBarrel(
                RestBlock.from(barrel.getBlock()),
                barrel
                        .getWatchers()
                        .stream()
                        .map(Player::getUuid)
                        .toArray(UUID[]::new),
                RestInventory.from(barrel.getInventory()),
                barrel.getDirection().name()
        );
    }
}
