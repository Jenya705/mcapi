package com.github.jenya705.mcapi.server.entity;

import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.error.BotNotPermittedException;
import com.github.jenya705.mcapi.permission.PermissionFlag;
import com.github.jenya705.mcapi.server.util.PermissionUtils;
import com.github.jenya705.mcapi.server.util.Selector;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public interface AbstractBot {

    BotEntity getEntity();

    List<BotLinkEntity> getLinks();

    default boolean hasPermission(String permission, UUID target) {
        return getPermissionEntity(permission, target).isToggled();
    }

    default PermissionFlag getPermissionFlag(String permission, UUID target) {
        return getPermissionEntity(permission, target).getPermissionFlag();
    }

    BotPermissionEntity getPermissionEntity(String permission, UUID target);

    default BotPermissionEntity getPermissionEntity(String permission) {
        return getPermissionEntity(permission, BotPermissionEntity.identityTarget);
    }

    default BotPermissionEntity getPermissionEntity(String permission, UUIDHolder uuidHolder) {
        if (uuidHolder instanceof Block) {
            return getPermissionEntity(permission, (Block) uuidHolder);
        }
        return getPermissionEntity(permission, uuidHolder.getUuid());
    }

    default BotPermissionEntity getPermissionEntity(String permission, Block block) {
        return getPermissionEntity(PermissionUtils.blockPermission(permission, block), block.getUuid());
    }

    default BotPermissionEntity getPermissionEntity(String permission, Selector<?> selector) {
        return getPermissionEntity(permission + selector.getPermissionName(), selector.getTarget());
    }

    default PermissionFlag getPermissionFlag(String permission) {
        return getPermissionFlag(permission, BotPermissionEntity.identityTarget);
    }

    default PermissionFlag getPermissionFlag(String permission, UUIDHolder uuidHolder) {
        if (uuidHolder instanceof Block) {
            return getPermissionFlag(permission, (Block) uuidHolder);
        }
        return getPermissionFlag(permission, uuidHolder.getUuid());
    }

    default PermissionFlag getPermissionFlag(String permission, Block block) {
        return getPermissionFlag(PermissionUtils.blockPermission(permission, block), block.getUuid());
    }

    default PermissionFlag getPermissionFlag(String permission, Selector<?> selector) {
        return getPermissionFlag(permission + selector.getPermissionName(), selector.getTarget());
    }

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

    default boolean hasPermission(String permission, Object obj) {
        if (obj instanceof UUIDHolder) {
            return hasPermission(permission, (UUIDHolder) obj);
        }
        if (obj instanceof Selector) {
            return hasPermission(permission, (Selector<?>) obj);
        }
        if (obj instanceof UUID) {
            return hasPermission(permission, (UUID) obj);
        }
        return false;
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

    default <T> Function<T, Mono<T>> mapGlobalPermission(String permission) {
        return it -> hasPermission(permission) ?
                Mono.just(it) : Mono.error(BotNotPermittedException.create(permission));
    }

    default <T> Function<T, Mono<T>> mapUuidPermission(String permission, UUID target) {
        return it -> hasPermission(permission, target) ?
                Mono.just(it) : Mono.error(BotNotPermittedException.create(permission));
    }

    default <T extends UUIDHolder> Function<T, Mono<T>> mapUuidHolderPermission(String permission) {
        return it -> hasPermission(permission, it) ?
                Mono.just(it) : Mono.error(BotNotPermittedException.create(permission));
    }

    default <T extends Block> Function<T, Mono<T>> mapBlockPermission(String permission) {
        return it -> hasPermission(permission, it) ?
                Mono.just(it) : Mono.error(BotNotPermittedException.create(permission));
    }

    default <T extends Selector<?>> Function<T, Mono<T>> mapSelectorPermission(String permission) {
        return it -> hasPermission(permission, it) ?
                Mono.just(it) : Mono.error(BotNotPermittedException.create(permission));
    }

    default <T> Function<T, Mono<T>> mapPermission(String permission) {
        return it -> hasPermission(permission, it) ?
                Mono.just(it) : Mono.error(BotNotPermittedException.create(permission));
    }

}
