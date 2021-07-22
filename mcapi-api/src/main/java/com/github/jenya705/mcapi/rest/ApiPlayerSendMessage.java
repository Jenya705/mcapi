package com.github.jenya705.mcapi.rest;

import java.util.UUID;

/**
 *
 * An api player send message represents rest methods to send messages
 *
 * @since 1.0
 * @author Jenya705
 */
public interface ApiPlayerSendMessage {

    void sendMessage(String name, String message);

    void sendMessage(UUID uniqueId, String message);

}
