package com.github.jenya705.mcapi.server.entity;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.error.BotNotPermittedException;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.util.Selector;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface AbstractBot {

    BotEntity getEntity();

    List<BotLinkEntity> getLinks();

    boolean hasPermission(String permission, UUID target);

    default boolean hasPermission(String permission) {
        return hasPermission(permission, BotPermissionEntity.identityTarget);
    }

    default boolean hasPermission(String permission, Player player) {
        return hasPermission(permission, player.getUuid());
    }

    default boolean hasPermission(String permission, OfflinePlayer player) {
        return hasPermission(permission, player.getUuid());
    }

    default boolean hasPermission(String permission, Selector<?> selector) {
        return hasPermission(permission + selector.getPermissionName(), selector.getTarget());
    }

    default void needPermission(String permission) {
        if (!hasPermission(permission)) {
            throw BotNotPermittedException.create(permission);
        }
    }

    default void needPermission(String permission, UUID target) {
        if (!hasPermission(permission, target)) {
            throw BotNotPermittedException.create(permission);
        }
    }

    default void needPermission(String permission, UUIDHolder uuid) {
        needPermission(permission, uuid.getUuid());
    }

    default void needPermission(String permission, Selector<?> selector) {
        needPermission(permission + selector.getPermissionName(), selector.getTarget());
    }
}
