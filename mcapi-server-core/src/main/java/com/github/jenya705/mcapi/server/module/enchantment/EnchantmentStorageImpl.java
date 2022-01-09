package com.github.jenya705.mcapi.server.module.enchantment;

import com.github.jenya705.mcapi.enchantment.Enchantment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jenya705
 */
public class EnchantmentStorageImpl implements EnchantmentStorage {

    private final Map<String, Enchantment> enchantments = new HashMap<>();

    @Override
    public Enchantment getEnchantment(String key) {
        return enchantments.get(key.toLowerCase(Locale.ROOT));
    }

    @Override
    public void addEnchantment(Enchantment enchantment) {
        enchantments.put(enchantment.getKey().toLowerCase(Locale.ROOT), enchantment);
    }
}
