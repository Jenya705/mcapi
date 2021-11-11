package com.github.jenya705.mcapi.rest.block;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.block.Barrel;
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

    private UUID[] watchers;
    private RestInventory inventory;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String direction;

    public static RestBarrel from(Barrel barrel) {
        return new RestBarrel(
                barrel
                        .getWatchers()
                        .stream()
                        .map(Player::getUuid)
                        .toArray(UUID[]::new),
                RestInventory.from(barrel.getInventory()),
                barrel.getDirection() == null ? null : barrel.getDirection().name()
        );
    }
}
