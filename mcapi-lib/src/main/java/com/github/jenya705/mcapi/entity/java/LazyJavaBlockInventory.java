package com.github.jenya705.mcapi.entity.java;

import com.github.jenya705.mcapi.JavaRestClient;
import com.github.jenya705.mcapi.inventory.JavaInventory;
import com.github.jenya705.mcapi.inventory.JavaItemStack;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import lombok.Builder;

/**
 * @author Jenya705
 */
@Builder
public class LazyJavaBlockInventory implements JavaInventory {

    public static LazyJavaBlockInventory of(JavaRestClient restClient,
                                        RestInventory inventory,
                                        String world,
                                        int blockX,
                                        int blockY,
                                        int blockZ) {
        return LazyJavaBlockInventory
                .builder()
                .restClient(restClient)
                .world(world)
                .blockX(blockX)
                .blockY(blockY)
                .blockZ(blockZ)
                .size(inventory.getSize())
                .build();
    }

    private final JavaRestClient restClient;
    private final int size;

    private final int blockX;
    private final int blockY;
    private final int blockZ;
    private final String world;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public JavaItemStack[] getAllItems() { // too expensive
        JavaItemStack[] allItems = new JavaItemStack[getSize()];
        for (int i = 0; i < getSize(); ++i) {
            allItems[i] = getItem(i);
        }
        return allItems;
    }

    @Override
    public JavaItemStack getItem(int index) {
        return restClient
                .getBlockInventoryItem(world, blockX, blockY, blockZ, index)
                .block();
    }

}
