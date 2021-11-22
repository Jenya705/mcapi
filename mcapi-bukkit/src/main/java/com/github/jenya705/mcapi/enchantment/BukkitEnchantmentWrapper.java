package com.github.jenya705.mcapi.enchantment;

import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitEnchantmentWrapper implements Enchantment {

    private final org.bukkit.enchantments.Enchantment enchantment;

    public static BukkitEnchantmentWrapper of(org.bukkit.enchantments.Enchantment enchantment) {
        if (enchantment == null) return null;
        return new BukkitEnchantmentWrapper(enchantment);
    }

    @Override
    public String getKey() {
        return enchantment.getKey().toString();
    }
}
