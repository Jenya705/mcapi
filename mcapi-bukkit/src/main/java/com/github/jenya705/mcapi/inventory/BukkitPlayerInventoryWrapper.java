package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;

/**
 * @author Jenya705
 */
public class BukkitPlayerInventoryWrapper implements PlayerInventory {

    private final BukkitInventoryWrapper inventoryWrapper;

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
    public int getSizeX() {
        return inventoryWrapper.getSizeX();
    }

    @Override
    public int getSizeY() {
        return inventoryWrapper.getSizeY();
    }

    @Override
    public ItemStack[] getAllItems() {
        return inventoryWrapper.getAllItems();
    }

    @Override
    public ItemStack getItem(int x, int y) {
        if (getSizeY() == y) {
            switch (x) {
                case 0: return getHelmet();
                case 1: return getChestplate();
                case 2: return getLeggings();
                case 3: return getBoots();
                case 4: return getMainHand();
                case 5: return getOffHand();
            }
        }
        return inventoryWrapper.getItem(x, y);
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
