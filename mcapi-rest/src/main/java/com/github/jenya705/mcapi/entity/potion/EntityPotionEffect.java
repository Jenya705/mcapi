package com.github.jenya705.mcapi.entity.potion;

import com.github.jenya705.mcapi.potion.PotionEffect;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
public class EntityPotionEffect implements PotionEffect {

    private PotionEffectType type;
    private int amplifier;
    private long duration;
    private boolean ambient;
    private boolean hidden;

}
