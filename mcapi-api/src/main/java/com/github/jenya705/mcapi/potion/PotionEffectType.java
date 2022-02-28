package com.github.jenya705.mcapi.potion;

import com.github.jenya705.mcapi.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jenya705
 */
public interface PotionEffectType {

    @NotNull NamespacedKey getKey();

    boolean isInstant();

}
