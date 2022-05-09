package com.github.jenya705.mcapi.entity.event;

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
public class EntityEntityDespawnEvent implements EntityDespawnEvent {

    private Entity despawned;

}
