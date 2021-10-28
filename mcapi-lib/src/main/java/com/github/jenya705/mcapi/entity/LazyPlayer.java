package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Builder
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class LazyPlayer implements Player {

    private final RestClient restClient;
    @Getter
    private final UUID uuid;

    private String name;
    private String type;

    @Override
    public String getType() {
        if (type == null) {
            loadFullPlayer();
        }
        return type;
    }

    @Override
    public String getName() {
        if (name == null) {
            loadFullPlayer();
        }
        return name;
    }

    @Override
    public void sendMessage(String message) {
        restClient.sendMessage(PlayerSelector.of(getUuid()), new DefaultMessage(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public void ban(String reason) {
        restClient.banPlayers(PlayerSelector.of(getUuid()), new DefaultMessage(reason));
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public void kick(String reason) {
        restClient.kickPlayers(PlayerSelector.of(getUuid()), new DefaultMessage(reason));
    }

    @Override
    public Location getLocation() {
        return restClient
                .getPlayerLocation(PlayerID.of(getUuid()))
                .blockOptional()
                .orElseThrow();
    }

    private void loadFullPlayer() {
        Player loaded = restClient
                .getPlayer(PlayerID.of(uuid))
                .blockOptional()
                .orElseThrow();
        name = loaded.getName();
        type = loaded.getType();
    }

}
