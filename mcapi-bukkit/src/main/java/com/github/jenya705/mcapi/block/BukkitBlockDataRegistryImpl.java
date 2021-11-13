package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.block.data.BukkitBarrelWrapper;
import com.github.jenya705.mcapi.block.data.BukkitChestWrapper;
import com.github.jenya705.mcapi.block.data.BukkitCommandBlockWrapper;
import com.github.jenya705.mcapi.block.data.BukkitFurnaceWrapper;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class BukkitBlockDataRegistryImpl implements BukkitBlockDataRegistry {

    private final Map<org.bukkit.Material, Function<org.bukkit.block.Block, BlockData>> blockDataCreators = new HashMap<>();

    public BukkitBlockDataRegistryImpl() {
        addCreator(Material.COMMAND_BLOCK, BukkitCommandBlockWrapper::of);
        addCreator(Material.CHEST, BukkitChestWrapper::of);
        addCreator(Material.BARREL, BukkitBarrelWrapper::of);
        addCreator(Material.FURNACE, BukkitFurnaceWrapper::of);
        addCreator(Material.BLAST_FURNACE, BukkitFurnaceWrapper::of);
        addCreator(Material.SMOKER, BukkitFurnaceWrapper::of);
    }

    @Override
    public BlockData getData(org.bukkit.block.Block block) {
        Function<org.bukkit.block.Block, BlockData> creator =
                blockDataCreators.get(block.getType());
        if (creator == null) return null;
        return creator.apply(block);
    }

    @SuppressWarnings("unchecked")
    private <T extends org.bukkit.block.Block> void addCreator(
            org.bukkit.Material material,
            Function<T, BlockData> creator
    ) {
        blockDataCreators.put(material, it -> creator.apply((T) it));
    }

}
