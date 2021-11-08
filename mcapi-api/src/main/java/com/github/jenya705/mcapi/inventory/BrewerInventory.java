package com.github.jenya705.mcapi.inventory;

/**
 * @author Jenya705
 */
public interface BrewerInventory extends Inventory {

    ItemStack getFuel();

    ItemStack getIngredient();

}
