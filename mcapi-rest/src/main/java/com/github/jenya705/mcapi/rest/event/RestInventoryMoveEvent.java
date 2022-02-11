package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.InventoryMoveEvent;
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
    private RestInventory inventory;
    @DefaultNull
    private RestInventory from;
    private RestItemStack item;
    @DefaultNull
    private Object holder;
    @DefaultNull
    private Player changer;

    public static RestInventoryMoveEvent from(InventoryMoveEvent event) {
        return new RestInventoryMoveEvent(
                event.getInventory() == null ?
                        null : RestInventory.from(event.getInventory()),
                Objects.equals(event.getInventory(), event.getFrom()) || event.getFrom() == null ?
                        null : RestInventory.from(event.getFrom()),
                RestItemStack.from(event.getItem()),
                event.getHolder() == null ? null : event.getHolder(),
                Objects.equals(event.getHolder(), event.getChanger()) ?
                        null : event.getChanger()
        );
    }

}
