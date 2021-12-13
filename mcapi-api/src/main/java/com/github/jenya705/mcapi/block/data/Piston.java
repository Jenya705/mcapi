package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.Directional;

/**
 * @author Jenya705
 */
public interface Piston extends BlockData, Directional {

    boolean isExtended();

    void setExtended(boolean extended);

}
