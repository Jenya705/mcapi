package com.github.jenya705.mcapi.server.module.authorization.debug;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public enum EasyPermissionManager implements PermissionManager {
    ALL((name, target) -> true),
    NONE((name, target) -> false)
    ;

    private final PermissionManager permissionManager;
    @Override
    public boolean hasPermission(String permission, UUID target) {
        return permissionManager.hasPermission(permission, target);
    }
}
