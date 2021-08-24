package com.github.jenya705.mcapi;

import java.util.Set;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ApiPlayer {

    String getName();

    UUID getUuid();

    void sendMessage(String message);

    void kick(String reason);

    void ban(String reason);

}
