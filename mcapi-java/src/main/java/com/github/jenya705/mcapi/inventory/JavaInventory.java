package com.github.jenya705.mcapi.inventory;

/**
 * @author Jenya705
 */
public interface JavaInventory extends Inventory {

    @Override
    JavaItemStack[] getAllItems();

    @Override
    JavaItemStack getItem(int x, int y);
}
