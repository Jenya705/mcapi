package com.github.jenya705.mcapi.server.module.message;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface TypedMessage extends Message {

    String getType();

    Message getMessage();

    default void send(CommandSender sender) {
        getMessage().send(sender);
    }

    default boolean ban(OfflinePlayer player) {
        return getMessage().ban(player);
    }

    default boolean kick(Player player) {
        return getMessage().kick(player);
    }
}
