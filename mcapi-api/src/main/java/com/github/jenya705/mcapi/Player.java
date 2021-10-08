package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface Player extends CommandSender, OfflinePlayer {

    void kick(String reason);

    Location getLocation();
}
