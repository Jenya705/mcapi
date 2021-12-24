package com.github.jenya705.mcapi.entity.inventory;

import com.github.jenya705.mcapi.inventory.IdentifiedInventoryItemStack;
import com.github.jenya705.mcapi.inventory.InventoryItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityIdentifiedInventoryItemStack implements IdentifiedInventoryItemStack {

    private String id;

    @Delegate
    private InventoryItemStack inventoryItemStack;

}
