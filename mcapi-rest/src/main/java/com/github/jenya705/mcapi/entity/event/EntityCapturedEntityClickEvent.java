package com.github.jenya705.mcapi.entity.event;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.event.CapturedEntityClickEvent;
import com.github.jenya705.mcapi.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCapturedEntityClickEvent implements CapturedEntityClickEvent {

    private Player player;
    private CapturableEntity entity;

    @Override
    public Player whoClicked() {
        return player;
    }
}
