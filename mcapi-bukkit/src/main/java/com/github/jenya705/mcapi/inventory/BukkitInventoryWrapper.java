package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;

/**
 * @author Jenya705
 */
public class BukkitInventoryWrapper implements JavaInventory {

    private final org.bukkit.inventory.Inventory bukkitInventory;

    private final JavaItemStack[] cachedItemStacks;

    public BukkitInventoryWrapper(org.bukkit.inventory.Inventory bukkitInventory) {
        this.bukkitInventory = bukkitInventory;
        cachedItemStacks = new JavaItemStack[bukkitInventory.getSize()];
    }

    public static BukkitInventoryWrapper of(org.bukkit.inventory.Inventory bukkitInventory) {
        if (bukkitInventory == null) return null;
        return new BukkitInventoryWrapper(bukkitInventory);
    }

    @Override
    public int getSize() {
        return bukkitInventory.getSize();
    }

    @Override
    public JavaItemStack[] getAllItems() {
        for (int i = 0; i < bukkitInventory.getSize(); ++i) {
            cacheItemIfNeed(i);
        }
        return cachedItemStacks;
    }

    @Override
    public JavaItemStack getItem(int item) {
        if (item > bukkitInventory.getSize()) return null;
        cacheItemIfNeed(item);
        return cachedItemStacks[item];
    }

    private void cacheItemIfNeed(int index) {
        if (cachedItemStacks[index] == null) {
            cachedItemStacks[index] = BukkitWrapper.itemStack(bukkitInventory.getItem(index));
        }
    }

}
