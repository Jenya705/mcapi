package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.event.QuitEvent;
import com.github.jenya705.mcapi.rest.event.RestQuitEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityQuitEvent implements QuitEvent {

    private OfflinePlayer player;

    public RestQuitEvent rest() {
        return RestQuitEvent.from(this);
    }
}
