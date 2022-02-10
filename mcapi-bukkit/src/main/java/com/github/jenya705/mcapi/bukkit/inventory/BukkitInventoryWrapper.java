package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.Getter;

/**
 * @author Jenya705
 */
public class BukkitInventoryWrapper implements Inventory {

    @Getter
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
    public int getSize() {
        return bukkitInventory.getSize();
    }

    @Override
    public ItemStack[] getAllItems() {
        for (int i = 0; i < bukkitInventory.getSize(); ++i) {
            cacheItemIfNeed(i);
        }
        return cachedItemStacks;
    }

    @Override
    public ItemStack getItem(int item) {
        if (item > bukkitInventory.getSize()) return null;
        cacheItemIfNeed(item);
        return cachedItemStacks[item];
    }

    @Override
    public void setItem(int item, ItemStack itemStack) {
        bukkitInventory.setItem(item, BukkitWrapper.itemStack(itemStack));
    }

    private void cacheItemIfNeed(int index) {
        if (cachedItemStacks[index] == null) {
            cachedItemStacks[index] = BukkitWrapper.itemStack(bukkitInventory.getItem(index));
        }
    }

}
