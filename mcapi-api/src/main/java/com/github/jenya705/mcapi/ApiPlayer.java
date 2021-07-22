package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 *
 * The root interface in the player hierarchy. <br>
 * An api player represents methods for all players (e.g. bedrock and java)
 *
 * @since 1.0
 * @see ApiPlayer
 * @author Jenya705
 */
public interface ApiPlayer {

    /**
     *
     * Returns player name
     *
     * @return player name
     */
    String getName();

    /**
     *
     * Returns player unique id
     *
     * @return player unique id
     */
    UUID getUniqueId();

    /**
     *
     * Sends message to player
     *
     * @param message Message
     */
    void sendMessage(String message);

}
