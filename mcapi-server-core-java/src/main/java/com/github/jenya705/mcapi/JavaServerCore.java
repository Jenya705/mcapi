package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.util.PlayerUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface JavaServerCore extends ServerCore {

    @Override
    JavaPlayer getPlayer(String name);

    @Override
    JavaPlayer getPlayer(UUID uuid);

    @Override
    Collection<? extends JavaPlayer> getPlayers();

    @Override
    default Optional<? extends JavaPlayer> getOptionalPlayer(String name) {
        return Optional.ofNullable(getPlayer(name));
    }

    @Override
    default Optional<? extends JavaPlayer> getOptionalPlayer(UUID uuid) {
        return Optional.ofNullable(getPlayer(uuid));
    }

    @Override
    default Optional<? extends JavaPlayer> getOptionalPlayerId(String id) {
        return PlayerUtils.getPlayer(id, this).map(player -> (JavaPlayer) player);
    }
}
