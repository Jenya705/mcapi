package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.*;
import com.github.jenya705.mcapi.block.state.CapturedState;
import com.github.jenya705.mcapi.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitShulkerBoxWrapper implements ShulkerBox {

    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final InventoryHolder inventoryHolderDelegate;
    @Delegate
    private final Watchable watchableDelegate;

    public BukkitShulkerBoxWrapper(org.bukkit.block.Block block) {
        CapturedState state = new SharedCapturedState(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
        inventoryHolderDelegate = new BukkitInventoryHolderWrapper(state);
        watchableDelegate = new BukkitWatchableWrapper(state);
    }

    public static BukkitShulkerBoxWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitShulkerBoxWrapper(block);
    }

}
