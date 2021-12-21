package com.github.jenya705.mcapi.menu;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;

/**
 * @author Jenya705
 */
public interface InventoryMenu extends Inventory {

    @Override
    MenuItem[] getAllItems();

    @Override
    MenuItem getItem(int item);

    @Override
    void setItem(int item, ItemStack itemStack);

    void setItem(int item, MenuItem menuItem);

}
