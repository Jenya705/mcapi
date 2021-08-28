package com.github.jenya705.mcapi;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BukkitPlayerWrapper extends BukkitCommandSenderWrapper implements JavaPlayer {

    private final Player player;

    public BukkitPlayerWrapper(Player player) {
        super(player);
        this.player = player;
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
        player.kick(Component.text(reason));
    }

    @Override
    public void ban(String reason) {
        player.banPlayer(reason);
    }

}
