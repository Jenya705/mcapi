package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitCommandSenderWrapper implements ApiCommandSender {

    private final CommandSender sender;

    public static ApiCommandSender of(CommandSender sender) {
        if (sender instanceof Player) {
            return BukkitPlayerWrapper.of((Player) sender);
        }
        return new BukkitCommandSenderWrapper(sender);
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
