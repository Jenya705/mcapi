package com.github.jenya705.mcapi.world;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.block.Block;

/**
 * @author Jenya705
 */
public interface World {

    String getName();

    Block getBlock(Location location);

    Block getBlock(int x, int y, int z);

    WorldDimension getWorldDimension();

    WorldWeather getWorldWeather();

}
