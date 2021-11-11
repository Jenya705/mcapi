package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.block.data.Watchable;
import com.github.jenya705.mcapi.inventory.BrewerInventory;
import com.github.jenya705.mcapi.inventory.InventoryHolder;

/**
 * @author Jenya705
 */
public interface BrewingStand extends InventoryHolder, Watchable, BlockData {

    @Override
    BrewerInventory getInventory();

    int getFuelLevel();

    int getBrewingTime();

}
