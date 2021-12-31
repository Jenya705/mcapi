package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.CommandSender;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitCommandSenderWrapper implements CommandSender {

    private final org.bukkit.command.CommandSender sender;

    public static BukkitCommandSenderWrapper of(org.bukkit.command.CommandSender sender) {
        return sender == null ? null : new BukkitCommandSenderWrapper(sender);
    }

    @Override
    public String getType() {
        if (sender instanceof ConsoleCommandSender)
            return "console";
        return "undefined";
    }

    @Override
    public String getId() {
        return sender instanceof ConsoleCommandSender ? "@c" :
                (sender instanceof Player ?
                        ((Player) sender).getUniqueId().toString() :
                        sender.getName()
                );
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
