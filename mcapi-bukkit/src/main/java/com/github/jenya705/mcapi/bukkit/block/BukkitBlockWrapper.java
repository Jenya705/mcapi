package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.bukkit.BukkitLocationWrapper;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitBlockWrapper implements Block {

    private final org.bukkit.block.Block bukkitBlock;

    public static BukkitBlockWrapper of(org.bukkit.block.Block bukkitBlock) {
        if (bukkitBlock == null) return null;
        return new BukkitBlockWrapper(bukkitBlock);
    }

    @Override
    public Location getLocation() {
        return BukkitLocationWrapper.of(bukkitBlock.getLocation());
    }

    @Override
    public Material getMaterial() {
        return VanillaMaterial.getMaterial(bukkitBlock.getType().name());
    }

    @Override
    public BlockData getBlockData() {
        return BukkitBlockDataRegistry.instance.getData(bukkitBlock);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other instanceof BukkitBlockWrapper blockWrapper) {
            return Objects.equals(blockWrapper.bukkitBlock, bukkitBlock);
        }
        return false;
    }

}
