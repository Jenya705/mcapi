package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Material;

/**
 * @author Jenya705
 */
public interface Block {

    Location getLocation();

    Material getMaterial();

    BlockData getBlockData();

}
