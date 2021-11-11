package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.block.data.Directional;

/**
 * @author Jenya705
 */
public interface CommandBlock extends BlockData, Directional {

    String getCommand();

}
