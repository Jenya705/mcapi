package com.github.jenya705.mcapi;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitWrapper {

    public ApiCommandSender sender(CommandSender sender) {
        if (sender instanceof Player) {
            return BukkitPlayerWrapper.of((Player) sender);
        }
        return BukkitCommandSenderWrapper.of(sender);
    }

    public ApiOfflinePlayer offlinePlayer(OfflinePlayer player) {
        if (player.isOnline() && player instanceof Player) {
            return BukkitPlayerWrapper.of((Player) player);
        }
        return BukkitOfflinePlayerWrapper.of(player);
    }

    public JavaPlayer player(Player player) {
        return BukkitPlayerWrapper.of(player);
    }

    public ApiLocation location(Location location) {
        return new BukkitLocationWrapper(location);
    }

}
