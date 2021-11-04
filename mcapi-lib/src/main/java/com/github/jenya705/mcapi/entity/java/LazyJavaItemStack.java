package com.github.jenya705.mcapi.entity.java;

import com.github.jenya705.mcapi.JavaRestClient;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.entity.inventory.EntityJavaItemStack;
import com.github.jenya705.mcapi.inventory.JavaItemStack;
import com.github.jenya705.mcapi.rest.inventory.RestJavaItemStack;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class LazyJavaItemStack {

    public JavaItemStack of(JavaRestClient restClient, RestJavaItemStack itemStack) {
        return new EntityJavaItemStack(
                VanillaMaterial.getMaterial(itemStack.getMaterial()),
                itemStack.getAmount(),
                itemStack.getCustomName()
        );
    }

}
