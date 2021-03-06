package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.block.Liter;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import org.bukkit.block.Block;

/**
 * @author Jenya705
 */
public class BukkitLiterWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.Lightable>
        implements Liter {

    public BukkitLiterWrapper(Block block) {
        super(block);
    }

    @Override
    public boolean isLit() {
        return data().isLit();
    }

    @Override
    public void setLit(boolean lit) {
        updateData(it -> it.setLit(lit));
    }

    @Override
    public com.github.jenya705.mcapi.block.Block getBlock() {
        return BukkitWrapper.block(getBukkitBlock());
    }
}
