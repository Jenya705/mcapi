package com.github.jenya705.mcapi.object;

import com.github.jenya705.mcapi.JavaPlayer;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Wrapper for bukkit player
 *
 * @author Jenya705
 */
@AllArgsConstructor
public class JavaPlayerWrapper implements JavaPlayer {

    private final Player player;

    @Override
    public @NotNull String getName() {
        return player.getName();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public float getExp() {
        return player.getExp();
    }

    @Override
    public int getLevel() {
        return player.getLevel();
    }

    @Override
    public Component getDisplayName() {
        return player.displayName();
    }

    @Override
    public long getPlayerTime() {
        return player.getPlayerTime();
    }

    @Override
    public void sendMessage(Component message) {
        player.sendMessage(message);
    }
}
