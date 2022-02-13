package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.data.BrewingStand;
import com.github.jenya705.mcapi.bukkit.block.AbstractBukkitBlockState;
import com.github.jenya705.mcapi.bukkit.block.BukkitStateContainer;
import com.github.jenya705.mcapi.bukkit.block.BukkitWatchableWrapper;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitBrewerInventory;
import com.github.jenya705.mcapi.inventory.BrewerInventory;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitBrewingStandWrapper
        extends AbstractBukkitBlockState<org.bukkit.block.BrewingStand>
        implements BrewingStand, BukkitStateContainer {

    @Delegate
    private final Watchable watchableDelegate;

    private BrewerInventory inventory;

    public BukkitBrewingStandWrapper(org.bukkit.block.Block block) {
        super(block);
        watchableDelegate = new BukkitWatchableWrapper(getState());
    }

    public static BukkitBrewingStandWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitBrewingStandWrapper(block);
    }

    @Override
    public BrewerInventory getInventory() {
        if (inventory == null) {
            inventory = new BukkitBrewerInventory(state().getInventory());
        }
        return inventory;
    }

    @Override
    public int getFuelLevel() {
        return state().getFuelLevel();
    }

    @Override
    public int getBrewingTime() {
        return state().getBrewingTime();
    }


}
