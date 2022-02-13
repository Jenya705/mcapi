package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.data.ShulkerBox;
import com.github.jenya705.mcapi.bukkit.block.*;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitShulkerBoxWrapper
        extends AbstractBukkitBlockState<org.bukkit.block.ShulkerBox>
        implements ShulkerBox {

    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final InventoryHolder inventoryHolderDelegate;
    @Delegate
    private final Watchable watchableDelegate;

    public BukkitShulkerBoxWrapper(org.bukkit.block.Block block) {
        super(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
        inventoryHolderDelegate = new BukkitInventoryHolderWrapper(getState());
        watchableDelegate = new BukkitWatchableWrapper(getState());
    }

    public static BukkitShulkerBoxWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitShulkerBoxWrapper(block);
    }

}
