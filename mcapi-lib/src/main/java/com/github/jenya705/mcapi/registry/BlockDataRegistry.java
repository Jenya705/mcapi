package com.github.jenya705.mcapi.registry;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.block.BlockData;

/**
 * @author Jenya705
 */
public interface BlockDataRegistry {

    Class<? extends BlockData> getBlockDataClass(String type);

    void addBlockDataClass(String type, Class<? extends BlockData> clazz);

    default void addBlockDataClass(Material material, Class<? extends BlockData> clazz) {
        addBlockDataClass(material.getKey(), clazz);
    }

}
