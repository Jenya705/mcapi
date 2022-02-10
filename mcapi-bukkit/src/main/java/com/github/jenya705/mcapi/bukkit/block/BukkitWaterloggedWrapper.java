package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.block.Waterlogged;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import org.bukkit.block.Block;

/**
 * @author Jenya705
 */
public class BukkitWaterloggedWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.Waterlogged>
        implements Waterlogged {

    public BukkitWaterloggedWrapper(Block block) {
        super(block);
    }

    @Override
    public boolean isWaterlogged() {
        return data().isWaterlogged();
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        updateData(it -> it.setWaterlogged(waterlogged));
    }

    @Override
    public com.github.jenya705.mcapi.block.Block getBlock() {
        return BukkitWrapper.block(getBukkitBlock());
    }

}
