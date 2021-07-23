package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermissionEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since 1.0
 * @author Jenya705
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApiServerPermissionEntity implements ApiPermissionEntity {

    private final String name;
    private final String target;
    private final String token;
    private final boolean enabled;

    public static ApiServerPermissionEntity target(String name, String target, String token, boolean enabled) {
        return new ApiServerPermissionEntity(name, target, token, enabled);
    }

    public static ApiServerPermissionEntity global(String name, String token, boolean enabled) {
        return new ApiServerPermissionEntity(name, null, token, enabled);
    }

}
