package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.inventory.PlayerInventory;

/**
 * @author Jenya705
 */
public class BukkitPlayerInventoryWrapper implements PlayerInventory {

    private final Inventory inventoryWrapper;

    private final org.bukkit.inventory.PlayerInventory bukkitPlayerInventory;

    public BukkitPlayerInventoryWrapper(org.bukkit.inventory.PlayerInventory bukkitPlayerInventory) {
        inventoryWrapper = new BukkitInventoryWrapper(bukkitPlayerInventory);
        this.bukkitPlayerInventory = bukkitPlayerInventory;
    }

    public static BukkitPlayerInventoryWrapper of(org.bukkit.inventory.PlayerInventory bukkitPlayerInventory) {
        if (bukkitPlayerInventory == null) return null;
        return new BukkitPlayerInventoryWrapper(bukkitPlayerInventory);
    }

    @Override
    public int getSize() {
        return inventoryWrapper.getSize() + 1;
    }

    @Override
    public ItemStack[] getAllItems() {
        ItemStack[] allItems = new ItemStack[getSize()];
        for (int i = 0; i < allItems.length; ++i) allItems[i] = getItem(i);
        return allItems;
    }

    @Override
    public ItemStack getItem(int item) {
        if (item == 41) return getMainHand();
        return inventoryWrapper.getItem(item);
    }

    @Override
    public void setItem(int item, ItemStack itemStack) {
        inventoryWrapper.setItem(item, itemStack);
    }

    @Override
    public ItemStack getHelmet() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getHelmet());
    }

    @Override
    public ItemStack getChestplate() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getChestplate());
    }

    @Override
    public ItemStack getLeggings() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getLeggings());
    }

    @Override
    public ItemStack getBoots() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getBoots());
    }

    @Override
    public ItemStack getMainHand() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getItemInMainHand());
    }

    @Override
    public ItemStack getOffHand() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getItemInOffHand());
    }

}
