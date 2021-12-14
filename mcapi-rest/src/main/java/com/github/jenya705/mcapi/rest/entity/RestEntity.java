package com.github.jenya705.mcapi.rest.entity;

import com.github.jenya705.mcapi.Vector3;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.rest.RestLocation;
import com.github.jenya705.mcapi.rest.RestShortBoundingBox;
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
public class RestEntity {

    private String type;
    private RestLocation location;
    private float yaw;
    private float pitch;
    private Vector3 velocity;
    private RestShortBoundingBox boundingBox;
    private int fireTicks;
    private Component customName;
    private boolean customNameVisible;
    private boolean silent;

    public static RestEntity from(Entity entity) {
        return new RestEntity(
                entity.getType(),
                RestLocation.from(entity.getLocation()),
                entity.getYaw(),
                entity.getPitch(),
                entity.getVelocity(),
                RestShortBoundingBox.from(entity.getBoundingBox()),
                entity.getFireTicks(),
                entity.customName(),
                entity.isCustomNameVisible(),
                entity.isSilent()
        );
    }
}
