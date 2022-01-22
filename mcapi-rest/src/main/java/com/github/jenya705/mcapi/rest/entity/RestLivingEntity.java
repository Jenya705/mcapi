package com.github.jenya705.mcapi.rest.entity;

import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.jackson.DefaultBoolean;
import com.github.jenya705.mcapi.jackson.DefaultFloat;
import com.github.jenya705.mcapi.jackson.DefaultNull;
import com.github.jenya705.mcapi.rest.RestLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

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

    public static RestLivingEntity from(LivingEntity livingEntity) {
        return new RestLivingEntity(
                livingEntity.getType(),
                RestLocation.from(
                        livingEntity.getLocation()
                ),
                livingEntity.customName(),
                livingEntity.hasAI(),
                livingEntity.getHealth()
        );
    }

}
