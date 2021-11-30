package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.Furnace;
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
public class RestFurnace {

    private RestInventory inventory;
    private UUID[] watchers;
    private String direction;
    private int burnTime;
    private int cookTimeTotal;
    private int cookTime;
    private boolean lit;

    public static RestFurnace from(Furnace furnace) {
        return new RestFurnace(
                RestInventory.from(furnace.getInventory()),
                furnace
                        .getWatchers()
                        .stream()
                        .map(Player::getUuid)
                        .toArray(UUID[]::new),
                furnace.getDirection().name(),
                furnace.getBurnTime(),
                furnace.getCookTimeTotal(),
                furnace.getCookTime(),
                furnace.isLit()
        );
    }
}
