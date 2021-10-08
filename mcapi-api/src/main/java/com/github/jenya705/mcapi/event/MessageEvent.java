package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.Player;

/**
 * @author Jenya705
 */
public interface MessageEvent {

    String getMessage();

    Player getAuthor();
}
