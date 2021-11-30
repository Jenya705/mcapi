package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.MessageEvent;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.event.RestMessageEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityMessageEvent implements MessageEvent {

    private String message;
    private Player author;

    public RestMessageEvent rest() {
        return RestMessageEvent.from(this);
    }
}
