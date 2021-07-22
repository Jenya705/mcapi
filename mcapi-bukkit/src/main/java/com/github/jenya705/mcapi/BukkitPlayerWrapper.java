package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitPlayerWrapper implements JavaPlayer {

    private final Player player;

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(Component message) {
        player.sendMessage(message);
    }

    public static BukkitPlayerWrapper of(Player player) {
        if (player == null) return null;
        return new BukkitPlayerWrapper(player);
    }
}
