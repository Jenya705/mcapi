package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.BoundingBox;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.Vector3;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public interface Entity extends UUIDHolder {

    String getType();

    Location getLocation();

    float getYaw();

    float getPitch();

    Vector3 getVelocity();

    BoundingBox getBoundingBox();

    int getFireTicks();

    Component customName();

    boolean isCustomNameVisible();

    boolean isSilent();

}
