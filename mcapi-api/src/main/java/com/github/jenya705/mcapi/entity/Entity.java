package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.UUIDHolder;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public interface Entity extends UUIDHolder {

    String getType();

    Location getLocation();

    Component customName();

}
