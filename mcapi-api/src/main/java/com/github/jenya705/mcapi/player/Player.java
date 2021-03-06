package com.github.jenya705.mcapi.player;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.PlayerInventory;

import java.util.Locale;

/**
 * @author Jenya705
 */
public interface Player extends LivingEntity, CommandSender, OfflinePlayer, InventoryHolder {

    void kick(String reason);

    void kill();

    void chat(String message);

    void runCommand(String command);

    @Override
    PlayerInventory getInventory();

    Inventory getEnderChest();

    GameMode getGameMode();

    PlayerAbilities getAbilities();

    default Locale getLocale() {
        return Locale.ENGLISH;
    }

    InventoryView openInventory(InventoryView inventory);

    InventoryView openInventory(Inventory inventory);

    void closeInventory();

    @Override
    default String getId() {
        return getUuid().toString();
    }

    default boolean isDead() {
        return getHealth() <= 0;
    }

    default boolean isAlive() {
        return !isDead();
    }

}
