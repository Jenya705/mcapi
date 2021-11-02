package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.JavaPlayer;

/**
 * @author Jenya705
 */
public interface JavaUnlinkEvent extends UnlinkEvent {

    @Override
    JavaPlayer getPlayer();

}
