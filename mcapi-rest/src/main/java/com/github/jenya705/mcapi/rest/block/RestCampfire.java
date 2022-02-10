package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.Campfire;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCampfire {

    private RestBlock block;
    private RestInventory inventory;
    private boolean signalFire;
    private boolean waterlogged;
    private boolean lit;
    private String direction;
    private int[] cookTime;
    private int[] cookTimeTotal;

    public static RestCampfire from(Campfire campfire) {
        return new RestCampfire(
                RestBlock.from(campfire.getBlock()),
                RestInventory.from(campfire.getInventory()),
                campfire.isSignalFire(),
                campfire.isWaterlogged(),
                campfire.isLit(),
                campfire.getDirection().name(),
                new int[]{
                        campfire.getCookTime(0),
                        campfire.getCookTime(1),
                        campfire.getCookTime(2),
                        campfire.getCookTime(3)
                },
                new int[]{
                        campfire.getCookTimeTotal(0),
                        campfire.getCookTimeTotal(1),
                        campfire.getCookTimeTotal(2),
                        campfire.getCookTimeTotal(3)
                }
        );
    }
}
