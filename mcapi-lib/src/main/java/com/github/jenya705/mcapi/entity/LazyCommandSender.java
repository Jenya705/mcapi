package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.RestClient;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class LazyCommandSender {

    public CommandSender of(RestClient client, RestCommandSender sender) {
        if (sender.getType().equalsIgnoreCase("player")) {
            return LazyPlayer.of(client, UUID.fromString(sender.getId()));
        }
        return null;
    }

}
