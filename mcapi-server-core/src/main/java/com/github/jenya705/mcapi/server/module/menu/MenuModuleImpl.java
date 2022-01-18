package com.github.jenya705.mcapi.server.module.menu;

import com.github.jenya705.mcapi.inventory.IdentifiedInventoryItemStack;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.menu.MenuItem;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnel;
import com.google.inject.Inject;

/**
 * @author Jenya705
 */
public class MenuModuleImpl extends AbstractApplicationModule implements MenuModule {

    private final EventTunnel eventTunnel;

    @Inject
    public MenuModuleImpl(ServerApplication application, EventTunnel eventTunnel) {
        super(application);
        this.eventTunnel = eventTunnel;
    }

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
