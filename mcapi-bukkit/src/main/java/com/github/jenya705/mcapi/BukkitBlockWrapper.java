package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.BukkitBlockDataRegistry;
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
