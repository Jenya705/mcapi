package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.util.PlayerUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface JavaServerCore extends ServerCore {

    Collection<? extends JavaPlayer> getJavaPlayers();

    JavaPlayer getJavaPlayer(String name);

    JavaPlayer getJavaPlayer(UUID uuid);

    default Collection<? extends ApiPlayer> getPlayers() {
        return getJavaPlayers();
    }

    default Optional<? extends JavaPlayer> getOptionalJavaPlayer(String name) {
        return Optional.ofNullable(getJavaPlayer(name));
    }

    default Optional<? extends JavaPlayer> getOptionalJavaPlayer(UUID uuid) {
        return Optional.ofNullable(getJavaPlayer(uuid));
    }

    default Optional<? extends JavaPlayer> getOptionalJavaPlayerId(String id) {
        return PlayerUtils.getPlayer(id, this).map(player -> (JavaPlayer) player);
    }

    default ApiPlayer getPlayer(String name) {
        return getJavaPlayer(name);
    }

    default ApiPlayer getPlayer(UUID uuid) {
        return getJavaPlayer(uuid);
    }

}
