package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermission;
import com.github.jenya705.mcapi.DefaultApiPermission;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @since 1.0
 * @author Jenya705
 */
@Slf4j
public class ApiServerPermissionContainerImpl implements ApiServerPermissionContainer {

    private final Map<String, ApiPermission> permissions = new HashMap<>();

    public ApiServerPermissionContainerImpl() {
        Arrays
                .stream(DefaultApiPermission.values())
                .forEach(this::add);
        log.info("Initialized permission container");
    }

    @Override
    public ApiPermission get(String name) {
        return permissions.getOrDefault(name.toLowerCase(Locale.ROOT), null);
    }

    @Override
    public void add(ApiPermission permission) {
        permissions.put(permission.getName().toLowerCase(Locale.ROOT), permission);
    }
}
