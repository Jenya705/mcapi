package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.block.data.Directional;
import com.github.jenya705.mcapi.block.data.Liter;
import com.github.jenya705.mcapi.block.data.Waterlogged;
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
