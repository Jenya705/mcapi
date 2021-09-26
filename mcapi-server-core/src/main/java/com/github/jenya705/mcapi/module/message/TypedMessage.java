package com.github.jenya705.mcapi.module.message;

import com.github.jenya705.mcapi.ApiCommandSender;

/**
 * @author Jenya705
 */
public interface TypedMessage extends SendMessage {

    String getType();

    SendMessage getMessage();

    default void send(ApiCommandSender sender) {
        getMessage().send(sender);
    }

}
