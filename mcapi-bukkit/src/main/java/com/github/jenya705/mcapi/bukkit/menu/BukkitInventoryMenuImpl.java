package com.github.jenya705.mcapi.bukkit.menu;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitInventoryViewWrapper;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitSharedInventoryViewWrapper;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.menu.MenuItem;
import com.github.jenya705.mcapi.player.Player;
import lombok.experimental.Delegate;

import java.util.Collection;

/**
 * @author Jenya705
 */
public class BukkitInventoryMenuImpl implements InventoryMenuView, BukkitInventoryViewWrapper {

    private final BukkitInventoryViewWrapper inventoryViewDelegate;

    @Delegate
    private final Inventory inventoryDelegate;

    private final BukkitMenuManager manager;

    public BukkitInventoryMenuImpl(BukkitMenuManager manager, BukkitInventoryViewWrapper inventoryView, Inventory inventory) {
        this.manager = manager;
        inventoryViewDelegate = inventoryView;
        inventoryDelegate = inventory;
    }

    public BukkitInventoryMenuImpl(BukkitMenuManager manager, Material airMaterial, Inventory inventory) {
        this(manager,
                new BukkitSharedInventoryViewWrapper(
                        inventory,
                        airMaterial == null ? VanillaMaterial.AIR : airMaterial
                ),
                inventory
        );
    }

    public BukkitInventoryMenuImpl(BukkitMenuManager manager, Inventory inventory) {
        this(manager, VanillaMaterial.AIR, inventory);
    }

    @Override
    public void clicked(Player player, int index) {
        ItemStack clickedItem = getItem(index);
        if (!(clickedItem instanceof MenuItem menuItem)) return;
        menuItem.getCallback().clicked(player);
    }

    @Override
    public void open(Player player) {
        manager.register(player, this);
        inventoryViewDelegate.open(player);
    }

    @Override
    public void close(Player player) {
        manager.unregister(player);
        inventoryViewDelegate.close(player);
    }

    @Override
    public Material getAirMaterial() {
        return inventoryViewDelegate.getAirMaterial();
    }

    @Override
    public Inventory getInventory() {
        return inventoryViewDelegate.getInventory();
    }

    @Override
    public Collection<? extends Player> getViewers() {
        return inventoryViewDelegate.getViewers();
    }

    @Override
    public boolean isUnique() {
        return inventoryViewDelegate.isUnique();
    }

    @Override
    public org.bukkit.inventory.Inventory getBukkitInventory() {
        return inventoryViewDelegate.getBukkitInventory();
    }
}
