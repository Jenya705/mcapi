package com.github.jenya705.mcapi.block;

/**
 * @author Jenya705
 */
public interface BukkitBlockDataRegistry {

    BukkitBlockDataRegistry instance = new BukkitBlockDataRegistryImpl();

    BlockData getData(org.bukkit.block.Block block);

}
