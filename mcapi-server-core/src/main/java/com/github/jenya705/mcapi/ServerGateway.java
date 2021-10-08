package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ServerGateway {

    void receiveMessage(Player player, String message);

    void join(Player player);

    void quit(OfflinePlayer player);
}
