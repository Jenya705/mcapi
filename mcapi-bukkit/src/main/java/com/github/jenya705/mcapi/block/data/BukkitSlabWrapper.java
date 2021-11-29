package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.block.AbstractBukkitBlockData;
import com.github.jenya705.mcapi.block.BukkitWaterloggedWrapper;
import com.github.jenya705.mcapi.block.Waterlogged;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitSlabWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.type.Slab>
        implements Slab {

    @Delegate
    private final Waterlogged waterloggedDelegate;

    public BukkitSlabWrapper(org.bukkit.block.Block block) {
        super(block);
        waterloggedDelegate = new BukkitWaterloggedWrapper(block);
    }

    @Override
    public SlabType getType() {
        return BukkitWrapper.slabType(data().getType());
    }

    @Override
    public void setType(SlabType type) {
        updateData(it -> it.setType(BukkitWrapper.slabType(type)));
    }

    public static BukkitSlabWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitSlabWrapper(block);
    }

}
