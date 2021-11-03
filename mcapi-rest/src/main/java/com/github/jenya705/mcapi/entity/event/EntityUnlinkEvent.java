package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.event.UnlinkEvent;
import com.github.jenya705.mcapi.rest.event.RestUnlinkEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityUnlinkEvent implements UnlinkEvent {

    private Player player;

    public RestUnlinkEvent rest() {
        return RestUnlinkEvent.from(this);
    }
}
