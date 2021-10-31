package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.BukkitBlockDataRegistry;
import com.github.jenya705.mcapi.world.World;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitWrapper {

    public CommandSender sender(org.bukkit.command.CommandSender sender) {
        if (sender instanceof Player) {
            return BukkitPlayerWrapper.of((Player) sender);
        }
        return BukkitCommandSenderWrapper.of(sender);
    }

    public OfflinePlayer offlinePlayer(org.bukkit.OfflinePlayer player) {
        if (player instanceof Player && player.isOnline()) {
            return BukkitPlayerWrapper.of((Player) player);
        }
        return BukkitOfflinePlayerWrapper.of(player);
    }

    public JavaPlayer player(Player player) {
        return BukkitPlayerWrapper.of(player);
    }

    public Location location(org.bukkit.Location location) {
        return BukkitLocationWrapper.of(location);
    }

    public World world(org.bukkit.World world) {
        return BukkitWorldWrapper.of(world);
    }

}
