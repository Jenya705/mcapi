package com.github.jenya705.mcapi.server.util;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.player.PlayerAbilities;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;

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
    public String getType() {
        return "player";
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
    public float getHealth() {
        return 0f;
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
    public InventoryView openInventory(InventoryView inventory, boolean sayAboutSelf) {
        return null;
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        return null;
    }

    @Override
    public void closeInventory() {

    }
}
