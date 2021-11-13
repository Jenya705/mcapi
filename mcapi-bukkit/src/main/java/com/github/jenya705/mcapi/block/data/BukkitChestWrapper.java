package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.block.BukkitInventoryHolderWrapper;
import com.github.jenya705.mcapi.block.BukkitWatchableWrapper;
import com.github.jenya705.mcapi.block.Chest;
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

    public BukkitChestWrapper(org.bukkit.block.Block bukkitChest) {
        CapturedState state = new SharedCapturedState(bukkitChest);
        inventoryHolderDelegate = new BukkitInventoryHolderWrapper(state);
        watchableDelegate = new BukkitWatchableWrapper(state);
        directionalDelegate = new BukkitDirectionalWrapper(bukkitChest);
    }

    public static BukkitChestWrapper of(org.bukkit.block.Block chest) {
        if (chest == null) return null;
        return new BukkitChestWrapper(chest);
    }
}
