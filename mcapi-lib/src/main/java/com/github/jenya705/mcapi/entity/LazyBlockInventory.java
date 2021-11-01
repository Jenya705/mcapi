package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
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
                .sizeX(inventory.getSizeX())
                .sizeY(inventory.getSizeY())
                .build();
    }

    private final RestClient restClient;
    private final int sizeX;
    private final int sizeY;

    private final int blockX;
    private final int blockY;
    private final int blockZ;
    private final String world;

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public ItemStack[] getAllItems() { // too expensive
        ItemStack[] allItems = new ItemStack[getSizeX() * getSizeY()];
        for (int x = 0; x < getSizeX(); ++x) {
            for (int y = 0; y < getSizeY(); ++y) {
                allItems[y * getSizeX() + x] = getItem(x, y);
            }
        }
        return allItems;
    }

    @Override
    public ItemStack getItem(int x, int y) {
        return restClient
                .getBlockInventoryItem(world, blockX, blockY, blockZ, x, y)
                .block();
    }
}
