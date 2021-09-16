package com.github.jenya705.mcapi.event;

import com.github.jenya705.mcapi.ApiPlayer;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface MessageEvent {

    String getMessage();

    ApiPlayer getAuthor();
}
