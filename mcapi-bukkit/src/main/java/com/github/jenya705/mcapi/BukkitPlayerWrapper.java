package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BukkitPlayerWrapper extends BukkitCommandSenderWrapper implements JavaPlayer {

    private final Player player;

    private final PlayerInventory inventory;

    public BukkitPlayerWrapper(Player player) {
        super(player);
        this.player = player;
        inventory = BukkitWrapper.playerInventory(player.getInventory());
    }

    public static BukkitPlayerWrapper of(Player player) {
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
}
