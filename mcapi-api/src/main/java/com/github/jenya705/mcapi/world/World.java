package com.github.jenya705.mcapi.world;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.entity.Entity;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface World {

    NamespacedKey getId();

    Block getBlock(Location location);

    Block getBlock(int x, int y, int z);

    WorldDimension getWorldDimension();

    WorldWeather getWorldWeather();

    Collection<Entity> getEntities();

}
