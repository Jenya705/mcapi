package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Liter;
import com.github.jenya705.mcapi.block.Waterlogged;
import com.github.jenya705.mcapi.inventory.InventoryHolder;

/**
 * @author Jenya705
 */
public interface Campfire extends InventoryHolder, BlockData, Waterlogged, Liter, Directional {

    boolean isSignalFire();

    void setSignalFire(boolean signalFire);

    int getCookTime(int index);

    int getCookTimeTotal(int index);

}
