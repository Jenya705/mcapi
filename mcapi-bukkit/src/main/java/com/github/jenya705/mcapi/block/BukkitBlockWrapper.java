package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitLocationWrapper;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;
import lombok.AllArgsConstructor;

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
}
