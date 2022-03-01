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

    default Vector3 asVector() {
        return Vector3.of(getX(), getY(), getZ());
    }

}
