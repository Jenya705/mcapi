package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.inventory.InventoryView;

/**
 * @author Jenya705
 */
public interface BukkitInventoryViewWrapper extends InventoryView {

    org.bukkit.inventory.Inventory getBukkitInventory();

}
