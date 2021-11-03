package com.github.jenya705.mcapi.inventory;

/**
 * @author Jenya705
 */
public interface Inventory {

    int getSize();

    ItemStack[] getAllItems();

    ItemStack getItem(int item);

}
