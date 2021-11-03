package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.entity.inventory.EntityItemStack;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class ItemUtils {

    public ItemStack empty() {
        return new EntityItemStack(
                VanillaMaterial.AIR,
                0, null
        );
    }

}
