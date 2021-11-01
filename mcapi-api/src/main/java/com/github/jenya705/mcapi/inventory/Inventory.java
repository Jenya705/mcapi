package com.github.jenya705.mcapi.inventory;

/**
 * @author Jenya705
 */
public interface Inventory {

    int getSizeX();

    int getSizeY();

    ItemStack[] getAllItems();

    ItemStack getItem(int x, int y);

}
