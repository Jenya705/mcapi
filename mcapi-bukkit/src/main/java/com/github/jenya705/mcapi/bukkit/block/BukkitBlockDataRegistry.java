package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.block.BlockData;

/**
 * @author Jenya705
 */
public interface BukkitBlockDataRegistry {

    BukkitBlockDataRegistry instance = new BukkitBlockDataRegistryImpl();

    BlockData getData(org.bukkit.block.Block block);

}
