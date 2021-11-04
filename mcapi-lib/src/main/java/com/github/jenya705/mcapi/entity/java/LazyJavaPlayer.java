package com.github.jenya705.mcapi.entity.java;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.entity.LazyPlayer;
import com.github.jenya705.mcapi.inventory.JavaPlayerInventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.message.ComponentMessage;
import com.github.jenya705.mcapi.message.DefaultMessage;
import com.github.jenya705.mcapi.rest.RestPlayer;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import lombok.Builder;
import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Builder
public class LazyJavaPlayer implements JavaPlayer {

    public static LazyJavaPlayer of(JavaRestClient client, UUID uuid) {
        return LazyJavaPlayer
                .builder()
                .restClient(client)
                .uuid(uuid)
                .build();
    }

    public static LazyJavaPlayer of(JavaRestClient client, RestPlayer player) {
        return LazyJavaPlayer
                .builder()
                .restClient(client)
                .uuid(player.getUuid())
                .name(player.getName())
                .type(player.getType())
                .build();
    }

    private final JavaRestClient restClient;
    @Getter
    private final UUID uuid;

    private String name;
    private String type;
    private JavaPlayerInventory inventory;

    @Override
    public String getType() {
        if (type == null) {
            loadFullPlayer();
        }
        return type;
    }

    @Override
    public String getId() {
        return uuid.toString();
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
        restClient.sendMessage(PlayerSelector.of(getUuid()), new DefaultMessage(message)).subscribe();
    }

    @Override
    public boolean hasPermission(String permission) {
        return restClient.getPlayerPermission(PlayerID.of(getUuid()), permission).block().isToggled();
    }

    @Override
    public void ban(String reason) {
        restClient.banPlayers(PlayerSelector.of(getUuid()), new DefaultMessage(reason)).subscribe();
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public void kick(String reason) {
        restClient.kickPlayers(PlayerSelector.of(getUuid()), new DefaultMessage(reason)).subscribe();
    }

    @Override
    public Location getLocation() {
        return restClient
                .getPlayerLocation(PlayerID.of(getUuid()))
                .blockOptional()
                .orElseThrow();
    }

    @Override
    public JavaPlayerInventory getInventory() {
        if (inventory == null) {
            inventory = restClient.getPlayerInventory(PlayerID.of(uuid)).block();
        }
        return inventory;
    }

    @Override
    public void sendMessage(Component component) {
        restClient.sendMessage(PlayerSelector.of(uuid), new ComponentMessage(component)).subscribe();
    }

    private void loadFullPlayer() {
        JavaPlayer loaded = restClient
                .getPlayer(PlayerID.of(uuid))
                .blockOptional()
                .orElseThrow();
        name = loaded.getName();
        type = loaded.getType();
    }
}
