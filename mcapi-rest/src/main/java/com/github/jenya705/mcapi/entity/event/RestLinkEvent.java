package com.github.jenya705.mcapi.entity.event;

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
public class RestLinkEvent {

    public static String type = "link";

    private boolean failed;
    private String[] declinePermissions;

    public String getType() {
        return type;
    }

    public static RestLinkEvent from(LinkEvent event) {
        return new RestLinkEvent(
                event.isFailed(),
                event.getDeclinePermissions()
        );
    }
}
