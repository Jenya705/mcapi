package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitOfflinePlayerWrapper implements OfflinePlayer {

    private final org.bukkit.OfflinePlayer player;

    public static BukkitOfflinePlayerWrapper of(org.bukkit.OfflinePlayer player) {
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
