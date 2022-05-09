package com.github.jenya705.mcapi.server.module.authorization.debug;

import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Getter
@RequiredArgsConstructor
public class ContainerAbstractBot implements AbstractBot {

    private final BotEntity entity;
    private final List<BotLinkEntity> links;
    private final PermissionManager permissionManager;

    @Override
    public BotPermissionEntity getPermissionEntity(String permission, UUID target) {
        return new BotPermissionEntity(
                entity.getId(),
                permission,
                target,
                false,
                permissionManager.hasPermission(permission, target)
        );
    }
}
