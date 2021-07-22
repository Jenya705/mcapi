package com.github.jenya705.mcapi;

import net.kyori.adventure.text.Component;

/**
 *
 * A java player represent methods only for java players
 *
 * @see ApiPlayer
 * @since 1.0
 * @author Jenya705
 */
public interface JavaPlayer extends ApiPlayer {

    @Override
    default void sendMessage(String message) {
        sendMessage(Component.text(message));
    }

    void sendMessage(Component message);

}
