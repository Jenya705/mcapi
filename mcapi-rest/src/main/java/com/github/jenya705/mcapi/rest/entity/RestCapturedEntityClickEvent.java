package com.github.jenya705.mcapi.rest.entity;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.event.CapturedEntityClickEvent;
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
public class RestCapturedEntityClickEvent {

    public static final String type = "captured_entity_click";

    private RestPlayer whoClicked;
    private CapturableEntity entity;

    public String getType() {
        return type;
    }

    public static RestCapturedEntityClickEvent from(CapturedEntityClickEvent event) {
        return new RestCapturedEntityClickEvent(
                RestPlayer.from(event.whoClicked()),
                event.getEntity()
        );
    }

}
