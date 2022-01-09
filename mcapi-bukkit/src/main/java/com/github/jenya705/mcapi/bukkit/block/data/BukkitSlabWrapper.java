package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Waterlogged;
import com.github.jenya705.mcapi.bukkit.BukkitWrapper;
import com.github.jenya705.mcapi.bukkit.block.AbstractBukkitBlockData;
import com.github.jenya705.mcapi.bukkit.block.BukkitWaterloggedWrapper;
import lombok.experimental.Delegate;
import org.bukkit.block.data.type.Slab;

/**
 * @author Jenya705
 */
public class BukkitSlabWrapper
        extends AbstractBukkitBlockData<Slab>
        implements com.github.jenya705.mcapi.block.data.Slab {

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
