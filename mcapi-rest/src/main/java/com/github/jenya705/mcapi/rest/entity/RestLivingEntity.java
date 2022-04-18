package com.github.jenya705.mcapi.rest.entity;

import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.jackson.DefaultBoolean;
import com.github.jenya705.mcapi.jackson.DefaultFloat;
import com.github.jenya705.mcapi.jackson.DefaultNull;
import com.github.jenya705.mcapi.rest.RestLocation;
import com.github.jenya705.mcapi.rest.potion.RestPotionEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestLivingEntity {

    private String type;
    @DefaultNull
    private RestLocation location;
    @DefaultNull
    private Component customName;
    @DefaultBoolean(true)
    private boolean ai;
    @DefaultFloat(20.0f)
    private float health;
    @DefaultNull
    private List<RestPotionEffect> effects;

    public static RestLivingEntity from(LivingEntity livingEntity) {
        return new RestLivingEntity(
                livingEntity.getType().toString(),
                RestLocation.from(
                        livingEntity.getLocation()
                ),
                livingEntity.customName(),
                livingEntity.hasAI(),
                livingEntity.getHealth(),
                livingEntity.getEffects().isEmpty() ?
                        null :
                        livingEntity
                                .getEffects()
                                .stream()
                                .map(RestPotionEffect::from)
                                .collect(Collectors.toList())
        );
    }

}
