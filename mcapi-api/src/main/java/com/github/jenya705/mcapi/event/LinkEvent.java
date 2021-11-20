package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface LinkEvent {

    boolean isFailed();

    Player getPlayer();

    String[] getDeclinePermissions();
}
