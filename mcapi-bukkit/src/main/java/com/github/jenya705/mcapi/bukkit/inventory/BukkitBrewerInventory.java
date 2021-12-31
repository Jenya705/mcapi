package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.inventory.BrewerInventory;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitBrewerInventory implements BrewerInventory {

    @Delegate
    private final Inventory inventoryDelegate;

    public BukkitBrewerInventory(org.bukkit.inventory.BrewerInventory inventory) {
        inventoryDelegate = new BukkitInventoryWrapper(inventory);
    }

    @Override
    public ItemStack getFuel() {
        return inventoryDelegate.getItem(3);
    }

    @Override
    public ItemStack getIngredient() {
        return inventoryDelegate.getItem(4);
    }
}
