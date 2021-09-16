package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.QuitEvent;
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

    public static final String type = "quit";

    private UUID player;

    public String getType() {
        return type;
    }

    public static RestQuitEvent from(QuitEvent event) {
        return new RestQuitEvent(
                event.getPlayer().getUuid()
        );
    }
}
