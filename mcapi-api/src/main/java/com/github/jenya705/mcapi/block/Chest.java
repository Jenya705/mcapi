package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.inventory.InventoryHolder;

import java.util.List;

/**
 * @author Jenya705
 */
public interface Chest extends BlockData, InventoryHolder {

    List<? extends Player> getWatchers();

}
