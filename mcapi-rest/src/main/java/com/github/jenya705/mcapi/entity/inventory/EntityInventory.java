package com.github.jenya705.mcapi.entity.inventory;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityInventory implements Inventory {

    private ItemStack[] items = new ItemStack[0];

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
