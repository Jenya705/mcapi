package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Liter;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.data.Furnace;
import com.github.jenya705.mcapi.bukkit.block.*;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitFurnaceInventoryWrapper;
import com.github.jenya705.mcapi.inventory.FurnaceInventory;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitFurnaceWrapper
        extends AbstractBukkitBlockState<org.bukkit.block.Furnace>
        implements Furnace {

    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Watchable watchableDelegate;
    @Delegate
    private final Liter literDelegate;

    private FurnaceInventory inventory;

    public BukkitFurnaceWrapper(org.bukkit.block.Block block) {
        this(new SharedCapturedState(block), block);
    }

    public BukkitFurnaceWrapper(CapturedState state, org.bukkit.block.Block block) {
        super(state, block);
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

}
