package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.MenuClickEvent;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestMenuClickEvent {

    private String id;
    private RestPlayer player;

    public static RestMenuClickEvent from(MenuClickEvent event) {
        return new RestMenuClickEvent(
                event.getId(),
                RestPlayer.from(event.getPlayer())
        );
    }
}
