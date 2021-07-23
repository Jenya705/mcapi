package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermissionEntity;

import java.util.Collection;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerPermissionRepository {

    void save(ApiPermissionEntity entity);

    void saveAll(Collection<ApiPermissionEntity> entities);

    ApiPermissionEntity getTarget(String name, String target, String token);

    ApiPermissionEntity getGlobal(String name, String token);

    void updateTarget(String name, String target, String token, boolean enabled);

    void updateGlobal(String name, String token, boolean enabled);

}
