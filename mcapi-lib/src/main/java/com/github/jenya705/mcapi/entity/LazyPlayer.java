package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Builder
public class LazyPlayer implements Player {

    private final RestClient client;
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
        client.sendMessage(PlayerSelector.of(getUuid()), new DefaultMessage(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public void ban(String reason) {
        client.banPlayers(PlayerSelector.of(getUuid()), new DefaultMessage(reason));
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public void kick(String reason) {
        client.kickPlayers(PlayerSelector.of(getUuid()), new DefaultMessage(reason));
    }

    @Override
    public Location getLocation() {
        return client
                .getPlayerLocation(PlayerID.of(getUuid()))
                .blockOptional()
                .orElseThrow();
    }

    private void loadFullPlayer() {
        Player loaded = client
                .getPlayer(PlayerID.of(uuid))
                .blockOptional()
                .orElseThrow();
        name = loaded.getName();
        type = loaded.getType();
    }

}
