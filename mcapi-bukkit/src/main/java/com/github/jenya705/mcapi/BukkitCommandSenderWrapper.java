package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitCommandSenderWrapper implements JavaCommandSender {

    private final CommandSender sender;

    public static BukkitCommandSenderWrapper of(CommandSender sender) {
        return sender == null ? null : new BukkitCommandSenderWrapper(sender);
    }

    @Override
    public void sendMessage(Component component) {
        sender.sendMessage(component);
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(MiniMessage.get().parse(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
