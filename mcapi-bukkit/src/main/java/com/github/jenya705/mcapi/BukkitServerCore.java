package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.wrapper.BukkitPlayerWrapper;
import org.bukkit.Bukkit;
import org.springframework.stereotype.Service;

/**
 * @author Jenya705
 */
@Service
public class BukkitServerCore implements JavaServerCore {

    @Override
    public JavaPlayer getPlayer(String name) {
        return new BukkitPlayerWrapper(Bukkit.getPlayer(name));
    }
}
