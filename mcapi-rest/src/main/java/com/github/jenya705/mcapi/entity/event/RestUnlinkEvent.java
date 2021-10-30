package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.event.UnlinkEvent;
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
public class RestUnlinkEvent {

    public static final String type = "unlink";

    private UUID player;

    public String getType() {
        return type;
    }

    public static RestUnlinkEvent from(UnlinkEvent event) {
        return new RestUnlinkEvent(
                event.getPlayer().getUuid()
        );
    }
}
