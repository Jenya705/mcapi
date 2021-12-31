package com.github.jenya705.mcapi.bukkit.enchantment;

import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitItemEnchantmentWrapper implements ItemEnchantment {

    @Delegate
    private final Enchantment enchantmentDelegate;
    private final int level;

    public static BukkitItemEnchantmentWrapper of(org.bukkit.enchantments.Enchantment enchantment, int level) {
        if (enchantment == null) return null;
        return new BukkitItemEnchantmentWrapper(new BukkitEnchantmentWrapper(enchantment), level);
    }

    @Override
    public int getLevel() {
        return level;
    }
}
