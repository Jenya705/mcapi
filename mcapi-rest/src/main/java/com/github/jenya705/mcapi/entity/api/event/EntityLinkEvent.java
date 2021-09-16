package com.github.jenya705.mcapi.entity.api.event;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.entity.event.RestLinkEvent;
import com.github.jenya705.mcapi.event.LinkEvent;
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
    private ApiPlayer player;
    private String[] declinePermissions;

    public RestLinkEvent rest() {
        return RestLinkEvent.from(this);
    }
}
