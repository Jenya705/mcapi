package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
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
                .size(restInventory.getSize())
                .playerID(playerID)
                .build();
    }

    private final RestClient restClient;
    private final int size;

    private final PlayerID playerID;

    @Override
    public ItemStack getHelmet() {
        return getItem(getSize() - 6);
    }

    @Override
    public ItemStack getChestplate() {
        return getItem(getSize() - 5);
    }

    @Override
    public ItemStack getLeggings() {
        return getItem(getSize() - 4);
    }

    @Override
    public ItemStack getBoots() {
        return getItem(getSize() - 3);
    }

    @Override
    public ItemStack getMainHand() {
        return getItem(getSize() - 2);
    }

    @Override
    public ItemStack getOffHand() {
        return getItem(getSize() - 1);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public ItemStack[] getAllItems() {
        ItemStack[] allItems = new ItemStack[getSize()];
        for (int i = 0; i < getSize(); ++i) {
            allItems[i] = getItem(i);
        }
        return allItems;
    }

    @Override
    public ItemStack getItem(int index) {
        return restClient.getPlayerInventoryItem(playerID, index).block();
    }
}
