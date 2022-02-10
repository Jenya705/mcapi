package com.github.jenya705.mcapi.bukkit.player;

import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(BukkitOfflinePlayerStorageImpl.class)
public interface BukkitOfflinePlayerStorage {

    org.bukkit.OfflinePlayer getPlayer(String name);

}
