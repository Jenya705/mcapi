package com.github.jenya705.mcapi.rest.event;

import com.github.jenya705.mcapi.event.SubscribeEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestSubscribeEvent {

    public static final String type = "subscribe";

    private String[] failed;

    public String getType() {
        return type;
    }

    public static RestSubscribeEvent from(SubscribeEvent event) {
        return new RestSubscribeEvent(
                event.getFailed()
        );
    }
}
