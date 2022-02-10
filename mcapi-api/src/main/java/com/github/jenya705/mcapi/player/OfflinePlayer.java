package com.github.jenya705.mcapi.player;

import com.github.jenya705.mcapi.UUIDHolder;

/**
 * @author Jenya705
 */
public interface OfflinePlayer extends UUIDHolder {

    String getName();

    void ban(String reason);

    boolean isOnline();
}
