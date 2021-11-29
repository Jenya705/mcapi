package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.*;

/**
 * @author Jenya705
 */
public interface Stairs extends BlockData, Directional, Waterlogged, Bisected {

    Shape getShape();

    void setShape(Shape shape);

}
