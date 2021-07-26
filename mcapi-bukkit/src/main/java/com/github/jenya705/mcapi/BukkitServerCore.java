package com.github.jenya705.mcapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * Implementation which methods used to link bukkit server core and server application
 *
 * @since 1.0
 * @author Jenya705
 */
public class BukkitServerCore implements JavaServerCore {

    private final JavaServerConfiguration config;

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
    public List<? extends JavaPlayer> getPlayers() {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(BukkitPlayerWrapper::new)
                .collect(Collectors.toList());
    }

    @Override
    public JavaServerConfiguration getConfig() {
        return config;
    }
}
