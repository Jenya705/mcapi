package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;

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
        // because bukkit player inventory working not correct for mcapi
        // we need to set real index (for bukkit) of item
        int realIndex;
        if (item < 27) realIndex=item+9;
        else if (item < 36) realIndex=item-27;
        else if (item < 40) realIndex=75-item;
        else if (item == 40) return getOffHand();
        else return getMainHand();
        return inventoryWrapper.getItem(realIndex);
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
