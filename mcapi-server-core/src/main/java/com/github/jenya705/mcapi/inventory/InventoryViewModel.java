package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.Material;
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
