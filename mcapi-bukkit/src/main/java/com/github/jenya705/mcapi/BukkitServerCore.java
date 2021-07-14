package com.github.jenya705.mcapi;

import org.bukkit.Bukkit;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Service
public class BukkitServerCore implements JavaServerCore {

    @Override
    public JavaPlayer getPlayer(String name) {
        return new BukkitPlayerWrapper(Bukkit.getPlayer(name));
    }

    @Override
    public JavaPlayer getPlayer(UUID uniqueId) {
        return new BukkitPlayerWrapper(Bukkit.getPlayer(uniqueId));
    }
}
