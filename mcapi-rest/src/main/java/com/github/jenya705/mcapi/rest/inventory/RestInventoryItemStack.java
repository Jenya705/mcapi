package com.github.jenya705.mcapi.rest.inventory;

import com.github.jenya705.mcapi.inventory.InventoryItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestInventoryItemStack {

    private int index;
    private RestItemStack item;

    public static RestInventoryItemStack from(InventoryItemStack inventoryItemStack) {
        return new RestInventoryItemStack(
                inventoryItemStack.getIndex(),
                RestItemStack.from(inventoryItemStack)
        );
    }

}
