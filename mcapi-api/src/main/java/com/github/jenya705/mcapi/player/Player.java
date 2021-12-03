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

    void kill();

    Location getLocation();

    PlayerInventory getInventory();

    Inventory getEnderChest();

    float getYaw();

    float getPitch();

    GameMode getGameMode();

    PlayerAbilities getAbilities();

    float getAbsorptionAmount();

    int getAirLeft();

    float getFallDistance();

    boolean isFalling();

    int getFireTicks();

    int getFoodLevel();

    float getFoodExhaustionLevel();

    float getFoodSaturationLevel();

    float getHealth();

    Location getSpawn();

    int getXpLevel();

    int getXpPercentage();

    @Override
    default String getId() {
        return getUuid().toString();
    }
}
