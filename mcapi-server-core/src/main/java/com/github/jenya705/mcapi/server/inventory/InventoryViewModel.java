package com.github.jenya705.mcapi.server.inventory;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryViewModel {

    private Material airMaterial;
    private ItemStack[] items;

}
