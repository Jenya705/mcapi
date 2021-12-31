package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.world.World;
import lombok.AllArgsConstructor;

import java.util.Objects;

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
    public World getWorld() {
        return BukkitWrapper.world(location.getWorld());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location other)) return false;
        if (obj == this) return true;
        return Objects.equals(other.getWorld(), getWorld()) &&
                Objects.equals(asVector(), other.asVector());
    }
}
