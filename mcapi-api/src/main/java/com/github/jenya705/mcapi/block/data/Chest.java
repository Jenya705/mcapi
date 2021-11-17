package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.Waterlogged;
import com.github.jenya705.mcapi.inventory.InventoryHolder;

/**
 * @author Jenya705
 */
public interface Chest extends BlockData, InventoryHolder, Watchable, Directional, Waterlogged {

}
