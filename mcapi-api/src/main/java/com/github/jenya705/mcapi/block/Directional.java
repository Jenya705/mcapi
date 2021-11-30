package com.github.jenya705.mcapi.block;

/**
 * @author Jenya705
 */
public interface Directional extends BlockData {

    BlockFace getDirection();

    void setDirection(BlockFace direction);

}
