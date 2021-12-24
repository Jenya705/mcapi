package com.github.jenya705.mcapi.rest.inventory;

import com.github.jenya705.mcapi.inventory.IdentifiedInventoryItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestIdentifiedInventoryItemStack {

    private String id;
    private int index;
    private RestItemStack item;

    public static RestIdentifiedInventoryItemStack from(IdentifiedInventoryItemStack inventoryItemStack) {
        return new RestIdentifiedInventoryItemStack(
                inventoryItemStack.getId(),
                inventoryItemStack.getIndex(),
                RestItemStack.from(inventoryItemStack)
        );
    }

}
