package com.github.jenya705.mcapi.bukkit.world;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitBlockWrapper;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.world.World;
import com.github.jenya705.mcapi.world.WorldDimension;
import com.github.jenya705.mcapi.world.WorldWeather;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitWorldWrapper implements World {

    private final org.bukkit.World bukkitWorld;

    public static BukkitWorldWrapper of(org.bukkit.World bukkitWorld) {
        if (bukkitWorld == null) return null;
        return new BukkitWorldWrapper(bukkitWorld);
    }

    @Override
    public String getName() {
        return bukkitWorld.getKey().toString();
    }

    @Override
    public Block getBlock(Location location) {
        if (!location.getWorld().getName().equalsIgnoreCase(getName())) return null;
        return getBlock(
                (int) location.getX(),
                (int) location.getY(),
                (int) location.getZ()
        );
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return BukkitBlockWrapper.of(
                bukkitWorld.getBlockAt(
                        x, y, z
                )
        );
    }

    @Override
    public WorldDimension getWorldDimension() {
        return switch (bukkitWorld.getEnvironment()) {
            case NORMAL -> WorldDimension.OVERWORLD;
            case NETHER -> WorldDimension.NETHER;
            case THE_END -> WorldDimension.END;
            default -> WorldDimension.UNKNOWN;
        };
    }

    @Override
    public WorldWeather getWorldWeather() {
        if (bukkitWorld.isThundering()) {
            return WorldWeather.THUNDER;
        }
        if (bukkitWorld.hasStorm()) {
            return WorldWeather.RAIN;
        }
        return WorldWeather.CLEAR;
    }

    @Override
    public Collection<Entity> getEntities() {
        return bukkitWorld
                .getEntities()
                .stream()
                .map(BukkitWrapper::entity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof World other)) return false;
        if (obj == this) return true;
        return Objects.equals(getName(), other.getName());
    }
}
