package com.github.jenya705.mcapi.entity;

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
public class RestItemStack {

    private String material;
    private int amount;
    private String customName;

    public static RestItemStack from(ItemStack itemStack) {
        return new RestItemStack(
                itemStack.getMaterial().getKey(),
                itemStack.getAmount(),
                itemStack.getCustomName()
        );
    }

}
