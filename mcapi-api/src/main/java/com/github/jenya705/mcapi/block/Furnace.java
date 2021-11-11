package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.block.data.Directional;
import com.github.jenya705.mcapi.block.data.Liter;
import com.github.jenya705.mcapi.block.data.Watchable;
import com.github.jenya705.mcapi.inventory.FurnaceInventory;
import com.github.jenya705.mcapi.inventory.InventoryHolder;

/**
 * @author Jenya705
 */
public interface Furnace extends InventoryHolder, Watchable, BlockData, Liter, Directional {

    @Override
    FurnaceInventory getInventory();

    int getCookTime();

    int getCookTimeTotal();

    int getBurnTime();

}
