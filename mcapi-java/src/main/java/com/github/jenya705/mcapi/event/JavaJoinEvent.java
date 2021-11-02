package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.JavaPlayer;

/**
 * @author Jenya705
 */
public interface JavaJoinEvent extends JoinEvent {

    @Override
    JavaPlayer getPlayer();

}
