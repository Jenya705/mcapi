package com.github.jenya705.mcapi.module.message;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiPlayer;

/**
 * @author Jenya705
 */
public interface Message {

    void send(ApiCommandSender sender);

    default boolean ban(ApiPlayer player) {
        return false;
    }

    default boolean kick(ApiPlayer player) {
        return false;
    }
}
