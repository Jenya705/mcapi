package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.entity.api.EntityItemStack;
import com.github.jenya705.mcapi.inventory.ItemStack;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class LazyItemStack {

    public ItemStack of(RestClient restClient, RestItemStack itemStack) {
        return new EntityItemStack(
                VanillaMaterial.getMaterial(itemStack.getMaterial()),
                itemStack.getAmount(),
                itemStack.getCustomName()
        );
    }

}
