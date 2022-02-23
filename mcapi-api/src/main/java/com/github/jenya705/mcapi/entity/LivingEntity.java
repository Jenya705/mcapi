package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.potion.PotionEffect;
import com.github.jenya705.mcapi.potion.PotionEffectType;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface LivingEntity extends Entity {

    Collection<PotionEffect> getEffects();

    float getHealth();

    boolean hasAI();

    void kill();

}
