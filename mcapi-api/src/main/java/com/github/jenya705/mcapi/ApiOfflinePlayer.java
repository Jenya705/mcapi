package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ApiOfflinePlayer extends ApiUUID {

    String getName();

    void ban(String reason);

    boolean isOnline();
}
