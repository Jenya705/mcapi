package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
public class EntityInventoryMoveEvent implements InventoryMoveEvent {

    private int destinationSlot;
    private int sourceSlot;
    private ItemStack item;
    private InventoryHolder destination;
    private InventoryHolder source;
    private InventoryHolder initiator;

}
