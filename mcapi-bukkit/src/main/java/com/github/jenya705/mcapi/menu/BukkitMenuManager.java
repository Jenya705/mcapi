package com.github.jenya705.mcapi.menu;

import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface BukkitMenuManager {

    void register(Player player, InventoryMenuView menu);

    void unregister(Player player);

}
