package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.error.BotNotPermittedException;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface AbstractBot {

    boolean hasPermission(String permission, UUID target);

    default boolean hasPermission(String permission) {
        return hasPermission(permission, BotPermissionEntity.identityTarget);
    }

    default boolean hasPermission(String permission, ApiPlayer player) {
        return hasPermission(permission, player.getUuid());
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

    default void needPermission(String permission, ApiPlayer player) {
        needPermission(permission, player.getUuid());
    }

}
