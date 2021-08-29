package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.util.PlayerUtils;
import com.github.jenya705.mcapi.util.Selector;
import com.github.jenya705.mcapi.util.SelectorImpl;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public interface JavaServerCore extends ServerCore {

    Collection<JavaPlayer> getJavaPlayers();

    JavaPlayer getJavaPlayer(String name);

    JavaPlayer getJavaPlayer(UUID uuid);

    default Collection<ApiPlayer> getPlayers() {
        return getJavaPlayers()
                .stream()
                .map((player) -> (ApiPlayer) player)
                .collect(Collectors.toList());
    }

    default Optional<JavaPlayer> getOptionalJavaPlayer(String name) {
        return Optional.ofNullable(getJavaPlayer(name));
    }

    default Optional<JavaPlayer> getOptionalJavaPlayer(UUID uuid) {
        return Optional.ofNullable(getJavaPlayer(uuid));
    }

    default Optional<JavaPlayer> getOptionalJavaPlayerId(String id) {
        return PlayerUtils.getPlayer(id, this).map(player -> (JavaPlayer) player);
    }

    default ApiPlayer getPlayer(String name) {
        return getJavaPlayer(name);
    }

    default ApiPlayer getPlayer(UUID uuid) {
        return getJavaPlayer(uuid);
    }

    default Selector<JavaPlayer> getJavaPlayersBySelector(String selector) {
        Selector<ApiPlayer> apiPlayerSelector = PlayerUtils.parseSelector(selector, this);
        return new SelectorImpl<>(
                apiPlayerSelector
                        .stream()
                        .filter(it -> it instanceof JavaPlayer)
                        .map(it -> (JavaPlayer) it)
                        .collect(Collectors.toSet()),
                apiPlayerSelector.getPermissionName(),
                apiPlayerSelector.getTarget()
        );
    }
}
