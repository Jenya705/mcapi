package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Liter;
import com.github.jenya705.mcapi.block.Watchable;
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
