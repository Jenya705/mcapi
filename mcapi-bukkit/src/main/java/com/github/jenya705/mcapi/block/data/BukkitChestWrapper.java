package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.*;
import com.github.jenya705.mcapi.block.state.CapturedState;
import com.github.jenya705.mcapi.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitChestWrapper implements Chest {

    @Delegate
    private final InventoryHolder inventoryHolderDelegate;
    @Delegate
    private final Watchable watchableDelegate;
    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Waterlogged waterloggedDelegate;

    public BukkitChestWrapper(org.bukkit.block.Block bukkitChest) {
        CapturedState state = new SharedCapturedState(bukkitChest);
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
