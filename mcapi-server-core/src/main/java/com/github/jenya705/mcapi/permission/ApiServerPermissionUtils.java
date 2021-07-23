package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermission;
import com.github.jenya705.mcapi.ApiPermissionEntity;
import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.exception.ApiPermissionDeniedException;
import lombok.experimental.UtilityClass;

/**
 * @since 1.0
 * @author Jenya705
 */
@UtilityClass
public class ApiServerPermissionUtils {

    public static boolean hasPermission(String name, String target, String token) {
        ApiServerApplication application = ApiServerApplication.getApplication();
        ApiServerPermissionContainer permissionContainer = application.getPermissionContainer();
        ApiServerPermissionRepository permissionRepository = application.getPermissionRepository();
        ApiPermissionEntity permissionEntity = permissionRepository.getTarget(name, target, token);
        if (permissionEntity != null) return permissionEntity.isEnabled();
        if (target != null) return hasPermission(name, null, token);
        return permissionContainer.get(name).isEnabledByDefault();
    }

    public static boolean hasPermission(ApiPermission permission, String target, String token) {
        return hasPermission(permission.getName(), target, token);
    }

    public static void exceptionPermission(String name, String target, String token) {
        if (!hasPermission(name, target, token)) throw new ApiPermissionDeniedException(name);
    }

    public static void exceptionPermission(ApiPermission permission, String target, String token) {
        exceptionPermission(permission.getName(), target, token);
    }

}
