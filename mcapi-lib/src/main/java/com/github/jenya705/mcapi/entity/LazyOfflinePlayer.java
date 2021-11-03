package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.message.DefaultMessage;
import com.github.jenya705.mcapi.rest.RestOfflinePlayer;
import com.github.jenya705.mcapi.selector.OfflinePlayerSelector;
import lombok.Builder;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Builder
public class LazyOfflinePlayer implements OfflinePlayer {

    public static LazyOfflinePlayer of(RestClient client, UUID uuid) {
        return LazyOfflinePlayer
                .builder()
                .restClient(client)
                .uuid(uuid)
                .build();
    }

    public static LazyOfflinePlayer of(RestClient client, RestOfflinePlayer offlinePlayer) {
        return LazyOfflinePlayer
                .builder()
                .restClient(client)
                .uuid(offlinePlayer.getUuid())
                .name(offlinePlayer.getName())
                .online(offlinePlayer.isOnline())
                .build();
    }

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
        restClient.banOfflinePlayer(OfflinePlayerSelector.of(uuid), new DefaultMessage(reason)).subscribe();
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
