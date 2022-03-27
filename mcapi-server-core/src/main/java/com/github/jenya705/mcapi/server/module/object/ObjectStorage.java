package com.github.jenya705.mcapi.server.module.object;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import com.google.inject.ImplementedBy;

import java.util.Locale;

/**
 * @author Jenya705
 */
@ImplementedBy(ObjectStorageImpl.class)
public interface ObjectStorage {

    boolean registerMaterial(Material material);

    Material getMaterial(NamespacedKey id);

    boolean registerPotionEffect(PotionEffectType effectType);

    PotionEffectType getPotionEffect(NamespacedKey id);

    boolean registerEnchantment(Enchantment enchantment);

    Enchantment getEnchantment(NamespacedKey id);

    default Material getMaterial(String id) {
        return getMaterial(NamespacedKey.from(id.toLowerCase(Locale.ROOT)));
    }

    default PotionEffectType getPotionEffect(String id) {
        return getPotionEffect(NamespacedKey.from(id.toLowerCase(Locale.ROOT)));
    }

    default Enchantment getEnchantment(String id) {
        return getEnchantment(NamespacedKey.from(id.toLowerCase(Locale.ROOT)));
    }

}
