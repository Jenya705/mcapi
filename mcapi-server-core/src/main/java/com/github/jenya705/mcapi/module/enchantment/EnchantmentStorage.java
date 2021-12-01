package com.github.jenya705.mcapi.module.enchantment;

import com.github.jenya705.mcapi.enchantment.Enchantment;

/**
 * @author Jenya705
 */
public interface EnchantmentStorage {

    Enchantment getEnchantment(String key);

    void addEnchantment(Enchantment enchantment);

}
