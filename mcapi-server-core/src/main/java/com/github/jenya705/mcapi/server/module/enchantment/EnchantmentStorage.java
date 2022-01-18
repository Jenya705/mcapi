package com.github.jenya705.mcapi.server.module.enchantment;

import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(EnchantmentStorageImpl.class)
public interface EnchantmentStorage {

    Enchantment getEnchantment(String key);

    void addEnchantment(Enchantment enchantment);

}
