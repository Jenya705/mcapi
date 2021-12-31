package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitLiterWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitWatchableWrapper;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitFurnaceInventoryWrapper;
import com.github.jenya705.mcapi.block.*;
import com.github.jenya705.mcapi.block.data.Furnace;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.FurnaceInventory;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitFurnaceWrapper implements Furnace {

    private final CapturedState state;

    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Watchable watchableDelegate;
    @Delegate
    private Liter literDelegate;

    private FurnaceInventory inventory;

    public BukkitFurnaceWrapper(org.bukkit.block.Block block) {
        this(new SharedCapturedState(block), block);
    }

    public BukkitFurnaceWrapper(CapturedState state, org.bukkit.block.Block block) {
        this.state = state;
        directionalDelegate = new BukkitDirectionalWrapper(block);
        watchableDelegate = new BukkitWatchableWrapper(state);
        literDelegate = new BukkitLiterWrapper(block);
    }

    public static BukkitFurnaceWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitFurnaceWrapper(block);
    }

    @Override
    public FurnaceInventory getInventory() {
        if (inventory == null) {
            inventory = new BukkitFurnaceInventoryWrapper(
                    state().getInventory()
            );
        }
        return inventory;
    }

    @Override
    public int getCookTime() {
        return state().getCookTime();
    }

    @Override
    public int getCookTimeTotal() {
        return state().getCookTimeTotal();
    }

    @Override
    public int getBurnTime() {
        return state().getBurnTime();
    }

    private org.bukkit.block.Furnace state() {
        return state.state();
    }

}
