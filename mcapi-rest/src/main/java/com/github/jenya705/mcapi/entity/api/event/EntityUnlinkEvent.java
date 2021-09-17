package com.github.jenya705.mcapi.entity.api.event;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.entity.event.RestUnlinkEvent;
import com.github.jenya705.mcapi.event.UnlinkEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityUnlinkEvent implements UnlinkEvent {

    private ApiPlayer player;

    public RestUnlinkEvent rest() {
        return RestUnlinkEvent.from(this);
    }

}