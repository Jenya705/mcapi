package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;

/**
 * @since 1.0
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitSenderWrapper implements ApiSender {

    private final CommandSender sender;

    @Override
    public String getName() {
        return sender.getName();
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }
}
