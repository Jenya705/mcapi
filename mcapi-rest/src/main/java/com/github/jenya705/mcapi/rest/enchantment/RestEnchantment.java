package com.github.jenya705.mcapi.rest.enchantment;

import com.github.jenya705.mcapi.enchantment.Enchantment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEnchantment {

    private String key;

    public static RestEnchantment from(Enchantment enchantment) {
        return new RestEnchantment(
                enchantment.getKey()
        );
    }

}
