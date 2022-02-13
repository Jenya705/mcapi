package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.inventory.ItemStack;

/**
 * @author Jenya705
 */
public interface InventoryMoveEvent {

    int getDestinationSlot();

    int getSourceSlot();

    ItemStack getItem();

    InventoryHolder getDestination();

    InventoryHolder getSource();

    InventoryHolder getInitiator();

}
