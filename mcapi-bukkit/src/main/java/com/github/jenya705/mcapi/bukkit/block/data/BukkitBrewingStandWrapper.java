package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.bukkit.block.BukkitWatchableWrapper;
import com.github.jenya705.mcapi.block.Watchable;
import com.github.jenya705.mcapi.block.data.BrewingStand;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.inventory.BrewerInventory;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitBrewerInventory;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitBrewingStandWrapper implements BrewingStand {

    @Delegate
    private final Watchable watchableDelegate;

    private final CapturedState state;

    private BrewerInventory inventory;

    public BukkitBrewingStandWrapper(org.bukkit.block.Block block) {
        state = new SharedCapturedState(block);
        watchableDelegate = new BukkitWatchableWrapper(state);
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

    private org.bukkit.block.BrewingStand state() {
        return state.state();
    }

}
