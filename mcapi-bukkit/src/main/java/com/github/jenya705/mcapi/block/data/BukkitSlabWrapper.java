package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BukkitWaterloggedWrapper;
import com.github.jenya705.mcapi.block.Waterlogged;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitSlabWrapper implements Slab {

    @Delegate
    private final Waterlogged waterloggedDelegate;

    private final org.bukkit.block.Block block;

    public BukkitSlabWrapper(org.bukkit.block.Block block) {
        this.block = block;
        waterloggedDelegate = new BukkitWaterloggedWrapper(block);
    }

    @Override
    public SlabType getType() {
        return SlabType.valueOf(
                ((org.bukkit.block.data.type.Slab)
                        block.getBlockData())
                        .getType().name()
        );
    }

    public static BukkitSlabWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitSlabWrapper(block);
    }

}
