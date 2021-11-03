package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;

/**
 * @author Jenya705
 */
public class BukkitPlayerInventoryWrapper implements JavaPlayerInventory {

    private final JavaInventory inventoryWrapper;

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
        return inventoryWrapper.getSize();
    }

    @Override
    public JavaItemStack[] getAllItems() {
        JavaItemStack[] allItems = new JavaItemStack[getSize()];
        for (int i = 0; i < allItems.length; ++i) allItems[i] = getItem(i);
        return allItems;
    }

    @Override
    public JavaItemStack getItem(int item) {
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
    public JavaItemStack getHelmet() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getHelmet());
    }

    @Override
    public JavaItemStack getChestplate() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getChestplate());
    }

    @Override
    public JavaItemStack getLeggings() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getLeggings());
    }

    @Override
    public JavaItemStack getBoots() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getBoots());
    }

    @Override
    public JavaItemStack getMainHand() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getItemInMainHand());
    }

    @Override
    public JavaItemStack getOffHand() {
        return BukkitWrapper.itemStack(bukkitPlayerInventory.getItemInOffHand());
    }

}
