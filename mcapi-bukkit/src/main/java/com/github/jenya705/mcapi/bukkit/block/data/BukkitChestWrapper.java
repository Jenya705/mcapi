package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.Waterlogged;
import com.github.jenya705.mcapi.block.data.Chest;
import com.github.jenya705.mcapi.bukkit.block.*;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitChestWrapper implements Chest, BukkitStateContainer {

    @Delegate
    private final InventoryHolder inventoryHolderDelegate;
    @Delegate
    private final Watchable watchableDelegate;
    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Waterlogged waterloggedDelegate;
    @Getter
    private final CapturedState state;

    public BukkitChestWrapper(org.bukkit.block.Block bukkitChest) {
        state = new SharedCapturedState(bukkitChest);
        inventoryHolderDelegate = new BukkitInventoryHolderWrapper(state);
        watchableDelegate = new BukkitWatchableWrapper(state);
        directionalDelegate = new BukkitDirectionalWrapper(bukkitChest);
        waterloggedDelegate = new BukkitWaterloggedWrapper(bukkitChest);
    }

    public static BukkitChestWrapper of(org.bukkit.block.Block chest) {
        if (chest == null) return null;
        return new BukkitChestWrapper(chest);
    }
}
