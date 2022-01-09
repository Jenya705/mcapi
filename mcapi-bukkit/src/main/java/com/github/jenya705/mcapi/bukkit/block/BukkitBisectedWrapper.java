package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.block.Bisected;
import com.github.jenya705.mcapi.block.Half;
import com.github.jenya705.mcapi.bukkit.BukkitWrapper;
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
