package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import lombok.Builder;

/**
 * @author Jenya705
 */
@Builder
public class LazyPlayerInventory implements PlayerInventory {

    public static LazyPlayerInventory of(RestClient restClient, RestInventory restInventory, PlayerID playerID) {
        return LazyPlayerInventory
                .builder()
                .restClient(restClient)
                .sizeX(restInventory.getSizeX())
                .sizeY(restInventory.getSizeY())
                .playerID(playerID)
                .build();
    }

    private final RestClient restClient;
    private final int sizeX;
    private final int sizeY;

    private final PlayerID playerID;

    @Override
    public ItemStack getHelmet() {
        return getItem(0, getSizeY());
    }

    @Override
    public ItemStack getChestplate() {
        return getItem(1, getSizeY());
    }

    @Override
    public ItemStack getLeggings() {
        return getItem(2, getSizeY());
    }

    @Override
    public ItemStack getBoots() {
        return getItem(3, getSizeY());
    }

    @Override
    public ItemStack getMainHand() {
        return getItem(4, getSizeY());
    }

    @Override
    public ItemStack getOffHand() {
        return getItem(5, getSizeY());
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public ItemStack[] getAllItems() {
        ItemStack[] allItems = new ItemStack[getSizeX() * getSizeY() + 6]; // all equipments (main hand is equipment)
        for (int x = 0; x < getSizeX(); ++x) {
            for (int y = 0; y < getSizeY(); ++y) {
                allItems[y * getSizeX() + x] = getItem(x, y);
            }
        }
        for (int i = 0; i < 6; ++i) {
            allItems[getSizeX() * getSizeY() + i] = getItem(i, getSizeY());
        }
        return allItems;
    }

    @Override
    public ItemStack getItem(int x, int y) {
        return restClient.getPlayerInventoryItem(playerID, x, y).block();
    }
}
