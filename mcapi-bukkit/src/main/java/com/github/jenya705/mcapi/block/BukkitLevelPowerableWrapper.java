package com.github.jenya705.mcapi.block;

import org.bukkit.block.Block;

/**
 * @author Jenya705
 */
public class BukkitLevelPowerableWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.AnaloguePowerable>
        implements LevelPowerable {

    public BukkitLevelPowerableWrapper(Block block) {
        super(block);
    }

    @Override
    public int getPower() {
        return data().getPower();
    }

    @Override
    public void setPower(int power) {
        updateData(it -> it.setPower(power));
    }

    @Override
    public int getMaxPower() {
        return data().getMaximumPower();
    }
}
