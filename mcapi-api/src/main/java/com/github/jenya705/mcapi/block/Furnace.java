package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.inventory.FurnaceInventory;
import com.github.jenya705.mcapi.inventory.InventoryHolder;

/**
 * @author Jenya705
 */
public interface Furnace extends InventoryHolder {

    FurnaceInventory getInventory();

    int getCookTime();

    int getCookTimeTotal();

    int getBurnTime();

}
