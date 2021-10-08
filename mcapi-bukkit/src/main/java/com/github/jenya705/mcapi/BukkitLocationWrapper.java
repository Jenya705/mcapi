package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitLocationWrapper implements Location {

    private final org.bukkit.Location location;

    public static BukkitLocationWrapper of(org.bukkit.Location location) {
        return location == null ? null : new BukkitLocationWrapper(location);
    }

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
