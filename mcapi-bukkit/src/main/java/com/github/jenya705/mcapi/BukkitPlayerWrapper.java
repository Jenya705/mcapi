package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
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
}
