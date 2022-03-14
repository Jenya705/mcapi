package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.QuitEvent;
import com.github.jenya705.mcapi.rest.RestOfflinePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestQuitEvent {

    public static final String type = "player_quit";

    private RestOfflinePlayer player;

    public String getType() {
        return type;
    }

    public static RestQuitEvent from(QuitEvent event) {
        return new RestQuitEvent(
                RestOfflinePlayer.from(event.getPlayer())
        );
    }
}
