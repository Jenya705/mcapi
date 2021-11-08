package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.inventory.BrewerInventory;
import com.github.jenya705.mcapi.inventory.InventoryHolder;

/**
 * @author Jenya705
 */
public interface BrewingStand extends InventoryHolder {

    BrewerInventory getInventory();

    int getFuelLevel();

    int getBrewingTime();

}
