package com.github.jenya705.mcapi.player;

import lombok.AllArgsConstructor;
import org.bukkit.GameMode;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitPlayerAbilities implements PlayerAbilities {

    private final org.bukkit.entity.Player player;

    @Override
    public boolean isInvulnerable() {
        return player.isInvulnerable();
    }

    @Override
    public boolean mayFly() {
        return player.getAllowFlight();
    }

    @Override
    public boolean isInstabuild() {
        return player.getGameMode() == GameMode.CREATIVE;
    }

    @Override
    public float getWalkSpeed() {
        return player.getWalkSpeed();
    }

    @Override
    public boolean mayBuild() {
        return player.getGameMode() == GameMode.SURVIVAL ||
                player.getGameMode() == GameMode.CREATIVE;
    }

    @Override
    public boolean isFlying() {
        return player.isFlying();
    }

    @Override
    public float getFlySpeed() {
        return player.getFlySpeed();
    }
}
