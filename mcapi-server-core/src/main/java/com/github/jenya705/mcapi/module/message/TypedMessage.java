package com.github.jenya705.mcapi.module.message;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;

/**
 * @author Jenya705
 */
public interface TypedMessage extends Message {

    String getType();

    Message getMessage();

    default void send(ApiCommandSender sender) {
        getMessage().send(sender);
    }

    default boolean ban(ApiPlayer player) {
        return getMessage().ban(player);
    }

    default boolean kick(ApiPlayer player) {
        return getMessage().kick(player);
    }
}
