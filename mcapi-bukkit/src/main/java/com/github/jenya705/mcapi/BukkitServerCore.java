package com.github.jenya705.mcapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 *
 * Implementation which methods used to link bukkit server core and server application
 *
 * @since 1.0
 * @author Jenya705
 */
public class BukkitServerCore implements JavaServerCore {

    private final ApiServerConfiguration config;

    public BukkitServerCore(JavaPlugin plugin) {
        config = new BukkitServerConfiguration(plugin);
    }

    @Override
    public JavaPlayer getPlayer(String name) {
        return BukkitPlayerWrapper.of(Bukkit.getPlayer(name));
    }

    @Override
    public JavaPlayer getPlayer(UUID uniqueId) {
        return BukkitPlayerWrapper.of(Bukkit.getPlayer(uniqueId));
    }

    @Override
    public ApiServerConfiguration getConfig() {
        return config;
    }
}
