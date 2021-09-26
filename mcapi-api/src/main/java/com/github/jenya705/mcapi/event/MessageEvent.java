package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.ApiPlayer;

/**
 * @author Jenya705
 */
public interface MessageEvent {

    String getMessage();

    ApiPlayer getAuthor();
}
