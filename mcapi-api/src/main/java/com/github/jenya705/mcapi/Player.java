package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;

/**
 * @author Jenya705
 */
public interface Player extends CommandSender, OfflinePlayer {

    void kick(String reason);

    Location getLocation();

    PlayerInventory getInventory();

    Inventory getEnderChest();

    @Override
    default String getId() {
        return getUuid().toString();
    }
}
