package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
public class EntityInventoryMoveEvent implements InventoryMoveEvent {

    private Inventory inventory;
    private Inventory from;
    private ItemStack item;
    private Object holder;
    private Player changer;

}
