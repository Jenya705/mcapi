package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitOfflinePlayerWrapper implements ApiOfflinePlayer {

    private final OfflinePlayer player;

    public static BukkitOfflinePlayerWrapper of(OfflinePlayer player) {
        return player == null ? null : new BukkitOfflinePlayerWrapper(player);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public void ban(String reason) {
        player.banPlayer(reason);
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }
}
