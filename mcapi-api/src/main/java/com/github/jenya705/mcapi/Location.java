package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.world.World;

/**
 * @author Jenya705
 */
public interface Location {

    double getX();

    double getY();

    double getZ();

    World getWorld();
}
