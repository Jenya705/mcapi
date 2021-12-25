package com.github.jenya705.mcapi.module.menu;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.inventory.IdentifiedInventoryItemStack;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.menu.MenuItem;
import com.github.jenya705.mcapi.module.web.tunnel.EventTunnel;

/**
 * @author Jenya705
 */
public class MenuModuleImpl extends AbstractApplicationModule implements MenuModule {

    @Bean
    private EventTunnel eventTunnel;

    @Override
    public MenuItem createCommandItem(ItemStack itemStack, String command) {
        return new DelegateMenuItem(
                itemStack,
                new CommandMenuCallback(command)
        );
    }

    @Override
    public MenuItem createEventTunnelItem(IdentifiedInventoryItemStack inventoryItemStack, int botId) {
        return new DelegateMenuItem(
                inventoryItemStack,
                new EventTunnelMenuCallback(
                        inventoryItemStack.getId(),
                        botId,
                        eventTunnel
                )
        );
    }

    @Override
    public void makeMenuItems(Inventory inventory, int botId) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack item = inventory.getItem(i);
            if (!(item instanceof IdentifiedInventoryItemStack)) continue;
            IdentifiedInventoryItemStack identifiedInventoryItemStack = (IdentifiedInventoryItemStack) item;
            inventory.setItem(i, createEventTunnelItem(identifiedInventoryItemStack, botId));
        }
    }
}
