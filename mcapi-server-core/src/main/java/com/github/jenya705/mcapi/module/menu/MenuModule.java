package com.github.jenya705.mcapi.module.menu;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.inventory.IdentifiedInventoryItemStack;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.menu.MenuItem;

/**
 * @author Jenya705
 */
public interface MenuModule {

    MenuItem createCommandItem(ItemStack itemStack, String command);

    default MenuItem createEventTunnelItem(IdentifiedInventoryItemStack inventoryItemStack, BotEntity botEntity) {
        return createEventTunnelItem(inventoryItemStack, botEntity.getId());
    }

    MenuItem createEventTunnelItem(IdentifiedInventoryItemStack inventoryItemStack, int botId);

    default void makeMenuItems(Inventory inventory, BotEntity botEntity) {
        makeMenuItems(inventory, botEntity.getId());
    }

    void makeMenuItems(Inventory inventory, int botId);

}
