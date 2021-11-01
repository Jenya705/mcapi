package com.github.jenya705.mcapi.entity.api;

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
public class EntityItemStack implements ItemStack {

    private Material material;
    private int amount;
    private String customName;
}
