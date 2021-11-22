package com.github.jenya705.mcapi.rest.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.rest.enchantment.RestItemEnchantment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestItemStack {

    private String material;
    private int amount;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Component customName;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RestItemEnchantment> enchantments;

    public static RestItemStack from(ItemStack itemStack) {
        return new RestItemStack(
                itemStack.getMaterial().getKey(),
                itemStack.getAmount(),
                itemStack.customName(),
                Optional.of(
                        itemStack
                                .getEnchantments()
                                .stream()
                                .map(RestItemEnchantment::from)
                                .collect(Collectors.toList())
                )
                        .filter(it -> !it.isEmpty())
                        .orElse(null)
        );
    }
}
