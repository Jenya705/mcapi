package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.jackson.DefaultInteger;
import com.github.jenya705.mcapi.jackson.DefaultNull;
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

    @DefaultInteger(-1)
    private int destinationSlot;
    private int sourceSlot;
    private RestItemStack item;
    @DefaultNull
    private InventoryHolder destination;
    private Object source;
    @DefaultNull
    private Object initiator;

    public static RestInventoryMoveEvent from(InventoryMoveEvent event) {
        return new RestInventoryMoveEvent(
                event.getDestinationSlot(),
                event.getSourceSlot(),
                RestItemStack.from(event.getItem()),
                event.getDestination(),
                Objects.equals(event.getDestination(), event.getSource()) ?
                        "destination" :
                        event.getSource(),
                Objects.equals(event.getDestination(), event.getInitiator()) ?
                        "destination" :
                        Objects.equals(event.getSource(), event.getInitiator()) ?
                                "source" :
                                event.getInitiator()
                );
    }

}
