package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface OfflinePlayer extends UUIDHolder {

    String getName();

    void ban(String reason);

    boolean isOnline();
}
