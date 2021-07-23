package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermissionEntity;
import com.github.jenya705.mcapi.ApiServerApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @since 1.0
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class SynchronizedApiServerPermissionEntity implements ApiPermissionEntity {

    private final String name;
    private final String target;
    private final String token;
    private boolean enabled;

    public static SynchronizedApiServerPermissionEntity target(String name, String target, String token, boolean enabled) {
        return new SynchronizedApiServerPermissionEntity(name, target, token, enabled);
    }

    public static SynchronizedApiServerPermissionEntity global(String name, String token, boolean enabled) {
        return new SynchronizedApiServerPermissionEntity(name, null, token, enabled);
    }

    public void setEnabled(boolean enabled) {
        if (isEnabled() == enabled) return;
        this.enabled = enabled;
        ApiServerApplication
                .getApplication()
                .getPermissionRepository()
                .updateTarget(getName(), getTarget(), getToken(), isEnabled());
    }

}
