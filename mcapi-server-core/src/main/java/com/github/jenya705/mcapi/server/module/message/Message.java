package com.github.jenya705.mcapi.server.module.message;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface Message {

    void send(CommandSender sender);

    default boolean ban(OfflinePlayer player) {
        return false;
    }

    default boolean kick(Player player) {
        return false;
    }
}
