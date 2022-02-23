package com.github.jenya705.mcapi.server.module.object;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;
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

    private final Map<String, Material> materials = new ConcurrentHashMap<>();
    private final Map<String, PotionEffectType> effects = new ConcurrentHashMap<>();

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
        return materials.putIfAbsent(material.getKey().toLowerCase(Locale.ROOT), material) == null;
    }

    @Override
    public Material getMaterial(String id) {
        return materials.get(id.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean registerPotionEffect(PotionEffectType effectType) {
        return effects.putIfAbsent(effectType.getKey().toLowerCase(Locale.ROOT), effectType) == null;
    }

    @Override
    public PotionEffectType getPotionEffect(String id) {
        return effects.get(id.toLowerCase(Locale.ROOT));
    }
}
