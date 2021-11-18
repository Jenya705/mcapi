package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BukkitPlayerWrapper extends BukkitCommandSenderWrapper implements Player {

    private final org.bukkit.entity.Player player;

    private final PlayerInventory inventory;
    private final Inventory enderChest;

    public BukkitPlayerWrapper(org.bukkit.entity.Player player) {
        super(player);
        this.player = player;
        enderChest = BukkitWrapper.inventory(player.getEnderChest());
        inventory = BukkitWrapper.playerInventory(player.getInventory());
    }

    public static BukkitPlayerWrapper of(org.bukkit.entity.Player player) {
        return player == null ? null : new BukkitPlayerWrapper(player);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(Component component) {
        player.sendMessage(component);
    }

    @Override
    public void kick(String reason) {
        BukkitUtils.notAsyncTask(() -> player.kick(Component.text(reason)));
    }

    @Override
    public void ban(String reason) {
        BukkitUtils.notAsyncTask(() -> player.banPlayer(reason));
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public Location getLocation() {
        return BukkitWrapper.location(player.getLocation());
    }

    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }

    @Override
    public Inventory getEnderChest() {
        return enderChest;
    }
}
