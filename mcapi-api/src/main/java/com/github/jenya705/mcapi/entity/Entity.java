package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.Vector3;

/**
 * @author Jenya705
 */
public interface Entity extends UUIDHolder {

    Location getLocation();

    float getYaw();

    float getPitch();

    Vector3 getVelocity();

}
