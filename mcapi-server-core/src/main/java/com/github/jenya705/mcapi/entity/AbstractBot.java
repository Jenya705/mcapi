package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ApiUUID;
import com.github.jenya705.mcapi.error.BotNotPermittedException;
import com.github.jenya705.mcapi.util.Selector;

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

    default boolean hasPermission(String permission, ApiPlayer player) {
        return hasPermission(permission, player.getUuid());
    }

    default boolean hasPermission(String permission, ApiOfflinePlayer player) {
        return hasPermission(permission, player.getUuid());
    }

    default boolean hasPermission(String permission, Selector<?> selector) {
        return hasPermission(permission + selector.getPermissionName(), selector.getTarget());
    }

    default void needPermission(String permission) {
        if (!hasPermission(permission)) {
            throw new BotNotPermittedException(permission);
        }
    }

    default void needPermission(String permission, UUID target) {
        if (!hasPermission(permission, target)) {
            throw new BotNotPermittedException(permission);
        }
    }

    default void needPermission(String permission, ApiUUID uuid) {
        needPermission(permission, uuid.getUuid());
    }

    default void needPermission(String permission, Selector<?> selector) {
        needPermission(permission + selector.getPermissionName(), selector.getTarget());
    }
}
