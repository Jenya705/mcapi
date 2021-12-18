package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface CapturedEntityClickEvent {

    CapturableEntity getEntity();

    Player whoClicked();

}
