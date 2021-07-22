package com.github.jenya705.mcapi.rest;

import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public interface JavaPlayerSendMessage extends ApiPlayerSendMessage {

    @Override
    default void sendMessage(String name, String message) {
        sendMessage(name, Component.text(message));
    }

    @Override
    default void sendMessage(UUID uniqueId, String message) {
        sendMessage(uniqueId, Component.text(message));
    }

    void sendMessage(String name, Component message);

    void sendMessage(UUID uniqueId, Component message);

}
