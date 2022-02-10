package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.block.Powerable;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import org.bukkit.block.Block;

/**
 * @author Jenya705
 */
public class BukkitPowerableWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.Powerable>
        implements Powerable {

    public BukkitPowerableWrapper(Block block) {
        super(block);
    }

    @Override
    public boolean isPowered() {
        return data().isPowered();
    }

    @Override
    public void setPowered(boolean powered) {
        updateData(it -> it.setPowered(powered));
    }

    @Override
    public com.github.jenya705.mcapi.block.Block getBlock() {
        return BukkitWrapper.block(getBukkitBlock());
    }
}
