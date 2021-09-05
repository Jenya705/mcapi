package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ApiOfflinePlayer {

    String getName();

    UUID getUuid();

    void ban(String reason);

    boolean isOnline();
}
