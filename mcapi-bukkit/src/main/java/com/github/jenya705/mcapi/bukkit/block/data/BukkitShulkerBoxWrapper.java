package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.data.ShulkerBox;
import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitInventoryHolderWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitStateContainer;
import com.github.jenya705.mcapi.bukkit.block.BukkitWatchableWrapper;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitShulkerBoxWrapper implements ShulkerBox, BukkitStateContainer {

    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final InventoryHolder inventoryHolderDelegate;
    @Delegate
    private final Watchable watchableDelegate;
    @Getter
    private final CapturedState state;

    public BukkitShulkerBoxWrapper(org.bukkit.block.Block block) {
        state = new SharedCapturedState(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
        inventoryHolderDelegate = new BukkitInventoryHolderWrapper(state);
        watchableDelegate = new BukkitWatchableWrapper(state);
    }

    public static BukkitShulkerBoxWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitShulkerBoxWrapper(block);
    }

}
