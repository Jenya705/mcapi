package com.github.jenya705.mcapi.bukkit.menu;

import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.player.Player;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(BukkitMenuManagerImpl.class)
public interface BukkitMenuManager {

    void register(Player player, InventoryMenuView menu);

    void unregister(Player player);

}
