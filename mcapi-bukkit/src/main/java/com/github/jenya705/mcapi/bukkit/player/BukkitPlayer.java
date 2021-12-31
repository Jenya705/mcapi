package com.github.jenya705.mcapi.bukkit.player;

import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface BukkitPlayer extends Player {

    org.bukkit.entity.Player getBukkit();

}
