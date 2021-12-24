package com.github.jenya705.mcapi.menu;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface InventoryMenu extends Inventory {

    void clicked(Player player, int index);

}
