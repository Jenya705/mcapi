package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.event.EntitySpawnEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEntitySpawnEvent {

    public static final String type = "entity_spawn";

    private Entity entity;

    public static RestEntitySpawnEvent from(EntitySpawnEvent event) {
        return new RestEntitySpawnEvent(
                event.getSpawned()
        );
    }

}
