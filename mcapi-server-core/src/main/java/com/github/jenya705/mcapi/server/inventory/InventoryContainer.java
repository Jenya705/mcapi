package com.github.jenya705.mcapi.server.inventory;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class InventoryContainer implements Inventory {

    private final ItemStack[] items;

    @Override
    public int getSize() {
        return items.length;
    }

    @Override
    public ItemStack[] getAllItems() {
        return items;
    }

    @Override
    public ItemStack getItem(int item) {
        return items[item];
    }

    @Override
    public void setItem(int item, ItemStack itemStack) {
        items[item] = itemStack;
    }
}
