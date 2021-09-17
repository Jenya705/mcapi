package com.github.jenya705.mcapi.entity.api.event;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.entity.event.RestQuitEvent;
import com.github.jenya705.mcapi.event.QuitEvent;
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

    private ApiOfflinePlayer player;

    public RestQuitEvent rest() {
        return RestQuitEvent.from(this);
    }
}