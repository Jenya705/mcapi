package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import lombok.Builder;

/**
 * @author Jenya705
 */
@Builder
public class LazyBlockInventory implements Inventory {

    public static LazyBlockInventory of(RestClient restClient,
                                        RestInventory inventory,
                                        String world,
                                        int blockX,
                                        int blockY,
                                        int blockZ) {
        return LazyBlockInventory
                .builder()
                .restClient(restClient)
                .world(world)
                .blockX(blockX)
                .blockY(blockY)
                .blockZ(blockZ)
                .size(inventory.getSize())
                .build();
    }

    private final RestClient restClient;
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
    public ItemStack[] getAllItems() { // too expensive
        ItemStack[] allItems = new ItemStack[getSize()];
        for (int i = 0; i < getSize(); ++i) {
            allItems[i] = getItem(i);
        }
        return allItems;
    }

    @Override
    public ItemStack getItem(int index) {
        return restClient
                .getBlockInventoryItem(world, blockX, blockY, blockZ, index)
                .block();
    }
}
