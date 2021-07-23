package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermission;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerPermissionContainer {

    ApiPermission get(String name);

    void add(ApiPermission permission);

}
