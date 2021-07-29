package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.wrapper.BukkitPlayerWrapper;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BukkitServerCore implements ServerCore {

    @Override
    public ApiPlayer getPlayer(String name) {
        return new BukkitPlayerWrapper(Bukkit.getPlayer(name));
    }

    @Override
    public ApiPlayer getPlayer(UUID uniqueId) {
        return new BukkitPlayerWrapper(Bukkit.getPlayer(uniqueId));
    }
}
