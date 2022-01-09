package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.data.Barrel;
import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitInventoryHolderWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitWatchableWrapper;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitBarrelWrapper implements Barrel {

    @Delegate
    private final InventoryHolder inventoryHolderDelegate;
    @Delegate
    private final Watchable watchableDelegate;
    @Delegate
    private final Directional directionalDelegate;

    public BukkitBarrelWrapper(org.bukkit.block.Block bukkitBarrel) {
        CapturedState state = new SharedCapturedState(bukkitBarrel);
        inventoryHolderDelegate = new BukkitInventoryHolderWrapper(state);
        watchableDelegate = new BukkitWatchableWrapper(state);
        directionalDelegate = new BukkitDirectionalWrapper(bukkitBarrel);
    }

    public static Barrel of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitBarrelWrapper(block);
    }

}
