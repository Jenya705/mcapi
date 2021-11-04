package com.github.jenya705.mcapi.entity.java;

import com.github.jenya705.mcapi.JavaRestClient;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.inventory.JavaItemStack;
import com.github.jenya705.mcapi.inventory.JavaPlayerInventory;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import lombok.Builder;

/**
 * @author Jenya705
 */
@Builder
public class LazyJavaPlayerInventory implements JavaPlayerInventory {

    public static LazyJavaPlayerInventory of(JavaRestClient restClient, RestInventory restInventory, PlayerID playerID) {
        return LazyJavaPlayerInventory
                .builder()
                .restClient(restClient)
                .size(restInventory.getSize())
                .playerID(playerID)
                .build();
    }

    private final JavaRestClient restClient;
    private final int size;

    private final PlayerID playerID;

    @Override
    public JavaItemStack getHelmet() {
        return getItem(getSize() - 6);
    }

    @Override
    public JavaItemStack getChestplate() {
        return getItem(getSize() - 5);
    }

    @Override
    public JavaItemStack getLeggings() {
        return getItem(getSize() - 4);
    }

    @Override
    public JavaItemStack getBoots() {
        return getItem(getSize() - 3);
    }

    @Override
    public JavaItemStack getMainHand() {
        return getItem(getSize() - 2);
    }

    @Override
    public JavaItemStack getOffHand() {
        return getItem(getSize() - 1);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public JavaItemStack[] getAllItems() {
        JavaItemStack[] allItems = new JavaItemStack[getSize()];
        for (int i = 0; i < getSize(); ++i) {
            allItems[i] = getItem(i);
        }
        return allItems;
    }

    @Override
    public JavaItemStack getItem(int index) {
        return restClient.getPlayerInventoryItem(playerID, index).block();
    }

}
