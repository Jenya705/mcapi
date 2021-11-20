package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface ServerLocalEventHandler {

    void receiveMessage(Player player, String message);

    void join(Player player);

    void quit(OfflinePlayer player);
}
