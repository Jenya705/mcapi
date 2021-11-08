package com.github.jenya705.mcapi.inventory;

/**
 * @author Jenya705
 */
public interface FurnaceInventory extends Inventory {

    ItemStack getSmelting();

    ItemStack getFuel();

    ItemStack getResult();

}
