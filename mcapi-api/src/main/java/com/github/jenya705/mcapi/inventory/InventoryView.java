package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.player.Player;

import java.util.Collection;

/**
 * @author Jenya705
 */
public interface InventoryView {

    void open(Player player);

    void close(Player player);

    Material getAirMaterial();

    Inventory getInventory();

    Collection<? extends Player> getViewers();

    boolean isUnique();

}
