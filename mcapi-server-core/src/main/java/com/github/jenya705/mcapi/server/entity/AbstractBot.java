package com.github.jenya705.mcapi.server.entity;

import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.error.BotNotPermittedException;
import com.github.jenya705.mcapi.server.util.PermissionUtils;
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

    default boolean hasPermission(String permission, UUIDHolder uuid) {
        if (uuid instanceof Block) {
            return hasPermission(permission, (Block) uuid);
        }
        return hasPermission(permission, uuid.getUuid());
    }

    default boolean hasPermission(String permission, Block block) {
        return hasPermission(PermissionUtils.blockPermission(permission, block), block.getUuid());
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
        if (uuid instanceof Block) {
            needPermission(permission, (Block) uuid);
        }
        else {
            needPermission(permission, uuid.getUuid());
        }
    }

    default void needPermission(String permission, Block block) {
        needPermission(PermissionUtils.blockPermission(permission, block), block.getUuid());
    }

    default void needPermission(String permission, Selector<?> selector) {
        needPermission(permission + selector.getPermissionName(), selector.getTarget());
    }
}
