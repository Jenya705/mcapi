package com.github.jenya705.mcapi.server.module.object;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(ObjectStorageImpl.class)
public interface ObjectStorage {

    boolean registerMaterial(Material material);

    Material getMaterial(String id);

    boolean registerPotionEffect(PotionEffectType effectType);

    PotionEffectType getPotionEffect(String id);

}
