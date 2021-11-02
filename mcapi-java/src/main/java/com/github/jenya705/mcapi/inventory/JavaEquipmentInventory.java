package com.github.jenya705.mcapi.inventory;

/**
 * @author Jenya705
 */
public interface JavaEquipmentInventory extends EquipmentInventory {

    @Override
    JavaItemStack getHelmet();

    @Override
    JavaItemStack getChestplate();

    @Override
    JavaItemStack getLeggings();

    @Override
    JavaItemStack getBoots();

    @Override
    JavaItemStack getMainHand();

    @Override
    JavaItemStack getOffHand();
}
