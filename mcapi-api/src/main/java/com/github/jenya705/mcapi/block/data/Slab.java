package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.Waterlogged;

/**
 * @author Jenya705
 */
public interface Slab extends BlockData, Waterlogged {

    enum SlabType {
        BOTTOM,
        TOP,
        DOUBLE
    }

    SlabType getType();

    void setType(SlabType type);

}
