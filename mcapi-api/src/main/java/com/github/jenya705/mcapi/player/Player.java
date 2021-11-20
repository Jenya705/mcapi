package com.github.jenya705.mcapi.player;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.OfflinePlayer;
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

    float getYaw();

    float getPitch();

    GameMode getGameMode();

    PlayerAbilities getAbilities();

    @Override
    default String getId() {
        return getUuid().toString();
    }
}
