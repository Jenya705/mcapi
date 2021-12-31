package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitLiterWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitWaterloggedWrapper;
import com.github.jenya705.mcapi.block.*;
import com.github.jenya705.mcapi.block.data.Campfire;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitCampfireInventoryWrapper;
import com.github.jenya705.mcapi.inventory.Inventory;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitCampfireWrapper implements Campfire {

    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Waterlogged waterloggedDelegate;
    @Delegate
    private final Liter literDelegate;

    private final Inventory campfireInventory;

    private final CapturedState state;

    private final org.bukkit.block.Block block;

    public BukkitCampfireWrapper(org.bukkit.block.Block block) {
        this.block = block;
        state = new SharedCapturedState(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
        waterloggedDelegate = new BukkitWaterloggedWrapper(block);
        literDelegate = new BukkitLiterWrapper(block);
        campfireInventory = new BukkitCampfireInventoryWrapper(state);
    }

    public static Campfire of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitCampfireWrapper(block);
    }

    @Override
    public boolean isSignalFire() {
        return data().isSignalFire();
    }

    @Override
    public void setSignalFire(boolean signalFire) {
        data().setSignalFire(signalFire);
    }

    @Override
    public int getCookTime(int index) {
        return state().getCookTime(index);
    }

    @Override
    public int getCookTimeTotal(int index) {
        return state().getCookTimeTotal(index);
    }

    @Override
    public Inventory getInventory() {
        return campfireInventory;
    }

    private org.bukkit.block.Campfire state() {
        return state.state();
    }

    private org.bukkit.block.data.type.Campfire data() {
        return (org.bukkit.block.data.type.Campfire) block.getBlockData();
    }

}
