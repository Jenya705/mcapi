package com.github.jenya705.mcapi.entity.api.event;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.entity.event.RestJoinEvent;
import com.github.jenya705.mcapi.event.JoinEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityJoinEvent implements JoinEvent {

    private Player player;

    public RestJoinEvent rest() {
        return RestJoinEvent.from(this);
    }
}
