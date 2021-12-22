package com.github.jenya705.mcapi.player;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;

/**
 * @author Jenya705
 */
public interface Player extends LivingEntity, CommandSender, OfflinePlayer {

    void kick(String reason);

    void kill();

    PlayerInventory getInventory();

    Inventory getEnderChest();

    GameMode getGameMode();

    PlayerAbilities getAbilities();

    @Override
    default String getId() {
        return getUuid().toString();
    }
}
