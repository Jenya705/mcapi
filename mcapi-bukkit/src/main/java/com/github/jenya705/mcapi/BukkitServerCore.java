package com.github.jenya705.mcapi;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 *
 * Implementation which methods used to link bukkit server core and server application
 *
 * @since 1.0
 * @author Jenya705
 */
public class BukkitServerCore implements JavaServerCore {

    @Override
    public JavaPlayer getPlayer(String name) {
        return BukkitPlayerWrapper.of(Bukkit.getPlayer(name));
    }

    @Override
    public JavaPlayer getPlayer(UUID uniqueId) {
        return BukkitPlayerWrapper.of(Bukkit.getPlayer(uniqueId));
    }
}
