package com.github.jenya705.mcapi.potion;

/**
 * @author Jenya705
 */
public interface PotionEffect {

    PotionEffectType getType();

    int getAmplifier();

    long getDuration();

    boolean isAmbient();

    boolean isHidden();

}
