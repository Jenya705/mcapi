package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.block.Chest;
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
public class RestChest {

    private UUID[] watchers;
    private RestInventory inventory;

    public static RestChest from(Chest chest) {
        return new RestChest(
                chest
                        .getWatchers()
                        .stream()
                        .map(Player::getUuid)
                        .toArray(UUID[]::new),
                RestInventory.from(chest.getInventory())
        );
    }
}
