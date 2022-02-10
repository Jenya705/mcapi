package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface InventoryMoveEvent {

    Inventory getInventory();

    Inventory getFrom();

    ItemStack getItem();

    /**
     *
     * Returns block (inventory holder), entity (inventory holder), null (no holder)
     *
     * @return holder which can be entity, block or null
     */
    Object getHolder();

    Player getChanger();

}
