package com.github.jenya705.mcapi.rest.potion;

import com.github.jenya705.mcapi.potion.PotionEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPotionEffect {

    private String key;
    private int amplifier;
    private long duration;
    private boolean ambient;
    private boolean hidden;

    public static RestPotionEffect from(PotionEffect effect) {
        return new RestPotionEffect(
                effect.getType().getKey(),
                effect.getAmplifier(),
                effect.getDuration(),
                effect.isAmbient(),
                effect.isHidden()
        );
    }

}
