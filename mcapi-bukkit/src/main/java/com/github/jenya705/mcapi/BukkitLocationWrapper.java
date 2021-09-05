package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import org.bukkit.Location;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitLocationWrapper implements ApiLocation {

    private final Location location;

    @Override
    public double getX() {
        return location.getX();
    }

    @Override
    public double getY() {
        return location.getY();
    }

    @Override
    public double getZ() {
        return location.getZ();
    }

    @Override
    public String getWorld() {
        return location.getWorld().getKey().getKey();
    }
}
