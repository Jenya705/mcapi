package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitUtils;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class BukkitBlockDataRegistryImpl implements BukkitBlockDataRegistry {

    private final Map<org.bukkit.Material, Function<org.bukkit.block.BlockState, BlockData>> blockDataCreators = new HashMap<>();

    public BukkitBlockDataRegistryImpl() {
        addCreator(Material.COMMAND_BLOCK, BukkitCommandBlockWrapper::of);
    }

    @Override
    public BlockData getData(org.bukkit.block.Block block) {
        Function<org.bukkit.block.BlockState, BlockData> creator =
                blockDataCreators.get(block.getType());
        if (creator == null) return null;
        return creator.apply(
                BukkitUtils.notAsyncSupplier(block::getState)
        );
    }

    @SuppressWarnings("unchecked")
    private <T extends org.bukkit.block.BlockState> void addCreator(
            org.bukkit.Material material,
            Function<T, BlockData> creator
    ) {
        blockDataCreators.put(material, it -> creator.apply((T) it));
    }

}
