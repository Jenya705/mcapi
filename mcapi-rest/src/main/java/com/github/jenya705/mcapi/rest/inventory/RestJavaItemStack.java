package com.github.jenya705.mcapi.rest.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jenya705.mcapi.inventory.JavaItemStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestJavaItemStack {

    private String material;
    private int amount;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Component customName;

    public static RestJavaItemStack from(JavaItemStack itemStack) {
        return new RestJavaItemStack(
                itemStack.getMaterial().getKey(),
                itemStack.getAmount(),
                itemStack.customName()
        );
    }

}
