package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.jackson.DefaultInteger;
import com.github.jenya705.mcapi.jackson.DefaultNull;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import com.github.jenya705.mcapi.rest.inventory.RestItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestInventoryMoveEvent {

    public static final String type = "inventory_move";

    @DefaultNull
    private RestInventory destination;
    private RestInventory from;
    @DefaultInteger(-1)
    private int destinationSlot;
    private int fromSlot;
    private RestItemStack item;
    @DefaultNull
    private Object holder;
    @DefaultNull
    private Player changer;

    public static RestInventoryMoveEvent from(InventoryMoveEvent event) {
        return new RestInventoryMoveEvent(
                event.getDestination() == null ?
                        null : RestInventory.from(event.getDestination()),
                Objects.equals(event.getDestination(), event.getFrom()) || event.getFrom() == null ?
                        null : RestInventory.from(event.getFrom()),
                event.getDestinationSlot(),
                event.getFromSlot(),
                RestItemStack.from(event.getItem()),
                event.getHolder() == null ? null : event.getHolder(),
                Objects.equals(event.getHolder(), event.getChanger()) ?
                        null : event.getChanger()
        );
    }

}
