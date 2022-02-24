package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface Beacon {

    Collection<LivingEntity> getAffectedEntities();

    PotionEffectType getPrimaryEffect();

    PotionEffectType getSecondaryEffect();

    int getTier();

    void setPrimaryEffect(PotionEffectType type);

    void setSecondaryEffect(PotionEffectType type);

}
