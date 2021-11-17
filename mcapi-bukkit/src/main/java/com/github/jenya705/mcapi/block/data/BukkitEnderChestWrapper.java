package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.block.BukkitWaterloggedWrapper;
import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.Waterlogged;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitEnderChestWrapper implements EnderChest {

    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Waterlogged waterloggedDelegate;

    public BukkitEnderChestWrapper(org.bukkit.block.Block enderChest) {
        directionalDelegate = new BukkitDirectionalWrapper(enderChest);
        waterloggedDelegate = new BukkitWaterloggedWrapper(enderChest);
    }

    public static BukkitEnderChestWrapper of(org.bukkit.block.Block enderChest) {
        if (enderChest == null) return null;
        return new BukkitEnderChestWrapper(enderChest);
    }

}
