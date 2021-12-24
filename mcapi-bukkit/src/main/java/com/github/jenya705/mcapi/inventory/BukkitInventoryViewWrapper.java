package com.github.jenya705.mcapi.inventory;

/**
 * @author Jenya705
 */
public interface BukkitInventoryViewWrapper extends InventoryView {

    org.bukkit.inventory.Inventory getBukkitInventory();

}
