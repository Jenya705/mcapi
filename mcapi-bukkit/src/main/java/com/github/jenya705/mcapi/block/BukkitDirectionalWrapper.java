package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitWrapper;
import org.bukkit.block.Block;

/**
 * @author Jenya705
 */
public class BukkitDirectionalWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.Directional>
        implements Directional {

    public BukkitDirectionalWrapper(Block block) {
        super(block);
    }

    @Override
    public BlockFace getDirection() {
        return BukkitWrapper.blockFace(data().getFacing());
    }

    @Override
    public void setDirection(BlockFace direction) {
        updateData(it -> it.setFacing(BukkitWrapper.blockFace(direction)));
    }

}
