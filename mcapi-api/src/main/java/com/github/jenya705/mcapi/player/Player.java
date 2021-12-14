package com.github.jenya705.mcapi.player;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;

/**
 * @author Jenya705
 */
public interface Player extends Entity, CommandSender, OfflinePlayer {

    void kick(String reason);

    void kill();

    PlayerInventory getInventory();

    Inventory getEnderChest();

    GameMode getGameMode();

    PlayerAbilities getAbilities();

    float getAbsorptionAmount();

    float getFallDistance();

    boolean isFalling();

    boolean isCrouching();

    boolean isSprinting();

    boolean isSwimming();

    boolean isFlyingWithElytra();

    int getAirLeft();

    int getFoodLevel();

    float getFoodExhaustionLevel();

    float getFoodSaturationLevel();

    float getHealth();

    Location getSpawn();

    int getXpLevel();

    int getXpPercentage();

    boolean isClimbing();

    @Override
    default String getId() {
        return getUuid().toString();
    }
}
