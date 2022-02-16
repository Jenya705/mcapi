package com.github.jenya705.mcapi.server.module.authorization.debug;

import java.util.UUID;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface PermissionManager {

    boolean hasPermission(String permission, UUID target);

}
