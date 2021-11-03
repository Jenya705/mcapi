package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.SubscribeEvent;
import com.github.jenya705.mcapi.rest.event.RestSubscribeEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntitySubscribeEvent implements SubscribeEvent {

    private String[] failed;

    public RestSubscribeEvent rest() {
        return RestSubscribeEvent.from(this);
    }
}
