package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.event.EntityDespawnEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEntityDespawnEvent {

    public static final String type = "entity_despawn";

    private Entity despawned;

    public static RestEntityDespawnEvent from(EntityDespawnEvent event) {
        return new RestEntityDespawnEvent(
                event.getDespawned()
        );
    }

}
