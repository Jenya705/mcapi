package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.object.JavaPlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BukkitServerCore implements JavaServerCore {

    @Override
    public JavaPlayer getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) return null;
        return new JavaPlayerWrapper(player);
    }

    @Override
    public @Nullable JavaPlayer getPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return null;
        return new JavaPlayerWrapper(player);
    }
}
