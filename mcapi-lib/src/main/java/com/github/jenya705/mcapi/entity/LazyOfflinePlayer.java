package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.DefaultMessage;
import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.selector.OfflinePlayerSelector;
import lombok.Builder;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Builder
public class LazyOfflinePlayer implements OfflinePlayer {

    private final RestClient restClient;
    private final UUID uuid;

    private boolean online;
    private String name;

    @Override
    public String getName() {
        if (name == null) {
            loadFullPlayer();
        }
        return name;
    }

    @Override
    public void ban(String reason) {
        restClient.banOfflinePlayer(OfflinePlayerSelector.of(uuid), new DefaultMessage(reason));
    }

    @Override
    public boolean isOnline() {
        if (name == null) {
            loadFullPlayer();
        }
        return online;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    private void loadFullPlayer() {
        OfflinePlayer loadedPlayer = restClient
                .getOfflinePlayer(PlayerID.of(uuid))
                .blockOptional()
                .orElseThrow();
        online = loadedPlayer.isOnline();
        name = loadedPlayer.getName();
    }

}
