package com.github.jenya705.mcapi.registry;

import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.entity.block.EntityChest;
import com.github.jenya705.mcapi.entity.block.EntityCommandBlock;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jenya705
 */
public class BlockDataRegistryImpl implements BlockDataRegistry {

    private final Map<String, Class<? extends BlockData>> blockDataMap = new HashMap<>();

    public BlockDataRegistryImpl() {
        addBlockDataClass(VanillaMaterial.COMMAND_BLOCK, EntityCommandBlock.class);
        addBlockDataClass(VanillaMaterial.CHEST, EntityChest.class);
    }

    @Override
    public Class<? extends BlockData> getBlockDataClass(String type) {
        return blockDataMap.get(type.toLowerCase(Locale.ROOT));
    }

    @Override
    public void addBlockDataClass(String type, Class<? extends BlockData> clazz) {
        blockDataMap.put(type.toLowerCase(Locale.ROOT), clazz);
    }
}
