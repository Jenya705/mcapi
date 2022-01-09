package com.github.jenya705.mcapi.server.module.menu;

import com.github.jenya705.mcapi.inventory.IdentifiedInventoryItemStack;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.menu.MenuItem;
import com.github.jenya705.mcapi.server.entity.BotEntity;

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
