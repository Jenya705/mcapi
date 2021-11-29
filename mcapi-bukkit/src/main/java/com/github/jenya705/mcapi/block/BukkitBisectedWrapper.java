package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitWrapper;
import org.bukkit.block.Block;

/**
 * @author Jenya705
 */
public class BukkitBisectedWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.Bisected>
        implements Bisected {

    public BukkitBisectedWrapper(Block block) {
        super(block);
    }

    @Override
    public Half getHalf() {
        return BukkitWrapper.half(data().getHalf());
    }

    @Override
    public void setHalf(Half half) {
        updateData(it -> it.setHalf(BukkitWrapper.half(half)));
    }

}
