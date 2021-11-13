package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.Directional;

/**
 * @author Jenya705
 */
public interface CommandBlock extends BlockData, Directional {

    String getCommand();

}
