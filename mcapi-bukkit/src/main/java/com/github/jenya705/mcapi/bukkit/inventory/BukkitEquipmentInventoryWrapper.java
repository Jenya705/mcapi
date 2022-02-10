package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.inventory.EquipmentInventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitEquipmentInventoryWrapper implements EquipmentInventory {

    private final org.bukkit.inventory.EntityEquipment bukkitEquipmentInventory;

    @Override
    public ItemStack getHelmet() {
        return BukkitWrapper.itemStack(bukkitEquipmentInventory.getHelmet());
    }

    @Override
    public ItemStack getChestplate() {
        return BukkitWrapper.itemStack(bukkitEquipmentInventory.getChestplate());
    }

    @Override
    public ItemStack getLeggings() {
        return BukkitWrapper.itemStack(bukkitEquipmentInventory.getLeggings());
    }

    @Override
    public ItemStack getBoots() {
        return BukkitWrapper.itemStack(bukkitEquipmentInventory.getBoots());
    }

    @Override
    public ItemStack getMainHand() {
        return BukkitWrapper.itemStack(bukkitEquipmentInventory.getItemInMainHand());
    }

    @Override
    public ItemStack getOffHand() {
        return BukkitWrapper.itemStack(bukkitEquipmentInventory.getItemInOffHand());
    }
}
