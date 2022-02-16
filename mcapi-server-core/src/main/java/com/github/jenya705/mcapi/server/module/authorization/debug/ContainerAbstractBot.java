package com.github.jenya705.mcapi.server.module.authorization.debug;

import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.List;

/**
 * @author Jenya705
 */
@Getter
@RequiredArgsConstructor
public class ContainerAbstractBot implements AbstractBot {

    private final BotEntity entity;
    private final List<BotLinkEntity> links;
    @Delegate
    private final PermissionManager permissionManager;

}
