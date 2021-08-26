package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitPlayerWrapper implements JavaPlayer {

    private final Player player;

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
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(Component component) {
        player.sendMessage(component);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
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
