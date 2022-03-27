package com.github.jenya705.mcapi.server.module.object;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import com.github.jenya705.mcapi.potion.VanillaPotionEffectType;
import com.google.inject.Singleton;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jenya705
 */
@Singleton
public class ObjectStorageImpl implements ObjectStorage {

    private final Map<NamespacedKey, Material> materials = new ConcurrentHashMap<>();
    private final Map<NamespacedKey, PotionEffectType> effects = new ConcurrentHashMap<>();
    private final Map<NamespacedKey, Enchantment> enchantments = new ConcurrentHashMap<>();

    public ObjectStorageImpl() {
        for (VanillaMaterial material: VanillaMaterial.values()) {
            registerMaterial(material);
        }
        for (VanillaPotionEffectType potionEffectType: VanillaPotionEffectType.values()) {
            registerPotionEffect(potionEffectType);
        }
    }

    @Override
    public boolean registerMaterial(Material material) {
        return materials.putIfAbsent(material.getKey(), material) == null;
    }

    @Override
    public Material getMaterial(NamespacedKey id) {
        return materials.get(id);
    }

    @Override
    public boolean registerPotionEffect(PotionEffectType effectType) {
        return effects.putIfAbsent(effectType.getKey(), effectType) == null;
    }

    @Override
    public PotionEffectType getPotionEffect(NamespacedKey id) {
        return effects.get(id);
    }

    @Override
    public boolean registerEnchantment(Enchantment enchantment) {
        return enchantments.putIfAbsent(enchantment.getKey(), enchantment) == null;
    }

    @Override
    public Enchantment getEnchantment(NamespacedKey id) {
        return enchantments.get(id);
    }
}
