package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BukkitBlockWrapper;
import com.github.jenya705.mcapi.world.World;
import com.github.jenya705.mcapi.world.WorldDimension;
import com.github.jenya705.mcapi.world.WorldWeather;
import lombok.AllArgsConstructor;

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
        org.bukkit.World.Environment environment = bukkitWorld.getEnvironment();
        switch (environment) {
            case NORMAL -> {
                return WorldDimension.OVERWORLD;
            }
            case NETHER -> {
                return WorldDimension.NETHER;
            }
            case THE_END -> {
                return WorldDimension.END;
            }
        }
        return WorldDimension.UNKNOWN;
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
}
