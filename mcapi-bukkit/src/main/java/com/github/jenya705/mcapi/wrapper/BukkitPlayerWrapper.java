package com.github.jenya705.mcapi.wrapper;

import com.github.jenya705.mcapi.ApiPlayer;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitPlayerWrapper implements ApiPlayer {

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
