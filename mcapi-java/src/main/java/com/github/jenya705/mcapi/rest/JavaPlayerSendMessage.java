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
    default void sendMessage(String name, String message, String token) {
        sendMessage(name, Component.text(message), token);
    }

    @Override
    default void sendMessage(UUID uniqueId, String message, String token) {
        sendMessage(uniqueId, Component.text(message), token);
    }

    void sendMessage(String name, Component message, String token);

    void sendMessage(UUID uniqueId, Component message, String token);

}
