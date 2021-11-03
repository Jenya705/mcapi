package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.event.LinkEvent;
import com.github.jenya705.mcapi.rest.event.RestLinkEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityLinkEvent implements LinkEvent {

    private boolean failed;
    private Player player;
    private String[] declinePermissions;

    public RestLinkEvent rest() {
        return RestLinkEvent.from(this);
    }
}
