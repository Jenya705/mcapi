package com.github.jenya705.mcapi.module.menu;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.inventory.IdentifiedInventoryItemStack;
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
}
