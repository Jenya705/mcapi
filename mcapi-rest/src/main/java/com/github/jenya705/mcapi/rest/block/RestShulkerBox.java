package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.ShulkerBox;
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
public class RestShulkerBox {

    private RestInventory inventory;
    private String direction;
    private UUID[] watchers;

    public static RestShulkerBox from(ShulkerBox shulkerBox) {
        return new RestShulkerBox(
                RestInventory.from(shulkerBox.getInventory()),
                shulkerBox.getDirection().name(),
                shulkerBox
                        .getWatchers()
                        .stream()
                        .map(Player::getUuid)
                        .toArray(UUID[]::new)
        );
    }
}
