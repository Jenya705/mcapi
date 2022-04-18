package com.github.jenya705.mcapi.server.util;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.player.PlayerAbilities;
import com.github.jenya705.mcapi.potion.PotionEffect;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class OnlinePlayerImitation implements Player {

    private final OfflinePlayer player;

    @Override
    public void sendMessage(String message) { }

    @Override
    public void sendMessage(Component component) { }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void ban(String reason) {
        player.ban(reason);
    }

    @Override
    public boolean isOnline() {
        return false;
    }

    @Override
    public UUID getUuid() {
        return player.getUuid();
    }

    @Override
    public NamespacedKey getType() {
        return NamespacedKey.minecraft("player");
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Component customName() {
        return Component.text(player.getName());
    }

    @Override
    public Collection<PotionEffect> getEffects() {
        return Collections.emptyList();
    }

    @Override
    public float getHealth() {
        return 20f;
    }

    @Override
    public boolean hasAI() {
        return false;
    }

    @Override
    public void kick(String reason) {

    }

    @Override
    public void kill() {

    }

    @Override
    public void chat(String message) {

    }

    @Override
    public void runCommand(String command) {

    }

    @Override
    public PlayerInventory getInventory() {
        return null;
    }

    @Override
    public Inventory getEnderChest() {
        return null;
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public PlayerAbilities getAbilities() {
        return null;
    }

    @Override
    public InventoryView openInventory(InventoryView inventory) {
        return inventory;
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        return null;
    }

    @Override
    public void closeInventory() {

    }
}
