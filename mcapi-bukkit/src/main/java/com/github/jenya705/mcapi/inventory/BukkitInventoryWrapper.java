package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;

/**
 * @author Jenya705
 */
public class BukkitInventoryWrapper implements Inventory {

    private final org.bukkit.inventory.Inventory bukkitInventory;

    private final ItemStack[] cachedItemStacks;

    public BukkitInventoryWrapper(org.bukkit.inventory.Inventory bukkitInventory) {
        this.bukkitInventory = bukkitInventory;
        cachedItemStacks = new ItemStack[bukkitInventory.getSize()];
    }

    public static BukkitInventoryWrapper of(org.bukkit.inventory.Inventory bukkitInventory) {
        if (bukkitInventory == null) return null;
        return new BukkitInventoryWrapper(bukkitInventory);
    }

    @Override
    public int getSizeX() {
        return bukkitInventory.getSize() == 5 ? 5 : 9;
    }

    @Override
    public int getSizeY() {
        return bukkitInventory.getSize() == 5 ? 1 : bukkitInventory.getSize() / 9;
    }

    @Override
    public ItemStack[] getAllItems() {
        for (int i = 0; i < bukkitInventory.getSize(); ++i) {
            cacheItemIfNeed(i);
        }
        return cachedItemStacks;
    }

    @Override
    public ItemStack getItem(int x, int y) {
        int index = y * getSizeX() + x;
        if (index > bukkitInventory.getSize()) return null;
        cacheItemIfNeed(index);
        return cachedItemStacks[index];
    }

    private void cacheItemIfNeed(int index) {
        if (cachedItemStacks[index] == null) {
            cachedItemStacks[index] = BukkitWrapper.itemStack(bukkitInventory.getItem(index));
        }
    }

}
