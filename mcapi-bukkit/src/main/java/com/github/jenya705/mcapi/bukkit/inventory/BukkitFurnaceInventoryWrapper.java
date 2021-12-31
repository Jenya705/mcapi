package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.inventory.FurnaceInventory;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitFurnaceInventoryWrapper implements FurnaceInventory {

    private final org.bukkit.inventory.FurnaceInventory inventory;

    @Delegate
    private final Inventory inventoryDelegate;

    public BukkitFurnaceInventoryWrapper(org.bukkit.inventory.FurnaceInventory inventory) {
        this.inventory = inventory;
        inventoryDelegate = new BukkitInventoryWrapper(inventory);
    }

    public static BukkitFurnaceInventoryWrapper of(org.bukkit.inventory.FurnaceInventory inventory) {
        if (inventory == null) return null;
        return new BukkitFurnaceInventoryWrapper(inventory);
    }

    @Override
    public ItemStack getSmelting() {
        return getItem(0);
    }

    @Override
    public ItemStack getFuel() {
        return getItem(1);
    }

    @Override
    public ItemStack getResult() {
        return getItem(2);
    }
}
