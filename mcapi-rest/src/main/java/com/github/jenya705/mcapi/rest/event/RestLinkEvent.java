package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.LinkEvent;
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
public class RestLinkEvent {

    public static final String type = "player_link";

    private boolean failed;
    private UUID player;
    private String[] declinePermissions;

    public String getType() {
        return type;
    }

    public static RestLinkEvent from(LinkEvent event) {
        return new RestLinkEvent(
                event.isFailed(),
                event.getPlayer().getUuid(),
                event.getDeclinePermissions()
        );
    }
}
