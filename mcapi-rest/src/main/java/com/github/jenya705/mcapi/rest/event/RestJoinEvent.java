package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.JoinEvent;
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
public class RestJoinEvent {

    public static final String type = "join";

    private UUID player;

    public String getType() {
        return type;
    }

    public static RestJoinEvent from(JoinEvent event) {
        return new RestJoinEvent(
                event.getPlayer().getUuid()
        );
    }

}
