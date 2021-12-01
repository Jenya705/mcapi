package com.github.jenya705.mcapi.entity.inventory;

import com.github.jenya705.mcapi.inventory.InventoryItemStack;
import com.github.jenya705.mcapi.inventory.ItemStack;
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
public class EntityInventoryItemStack implements InventoryItemStack {

    private int index;

    @Delegate
    private ItemStack itemStack;

}
