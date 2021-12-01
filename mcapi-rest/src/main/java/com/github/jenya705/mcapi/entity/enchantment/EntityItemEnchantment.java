package com.github.jenya705.mcapi.entity.enchantment;

import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityItemEnchantment implements ItemEnchantment {

    private int level;

    @Delegate
    private Enchantment enchantment;

}
