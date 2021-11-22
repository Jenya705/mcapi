package com.github.jenya705.mcapi.rest.enchantment;

import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestItemEnchantment {

    private String key;
    private int level;

    public static RestItemEnchantment from(ItemEnchantment itemEnchantment) {
        return new RestItemEnchantment(
                itemEnchantment.getKey(),
                itemEnchantment.getLevel()
        );
    }

}
