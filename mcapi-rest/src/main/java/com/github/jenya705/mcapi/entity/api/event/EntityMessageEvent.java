package com.github.jenya705.mcapi.entity.api.event;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.entity.event.RestMessageEvent;
import com.github.jenya705.mcapi.event.MessageEvent;
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
