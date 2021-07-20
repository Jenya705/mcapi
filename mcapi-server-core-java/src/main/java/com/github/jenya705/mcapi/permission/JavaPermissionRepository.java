package com.github.jenya705.mcapi.permission;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Jenya705
 */
public interface JavaPermissionRepository extends
        CrudRepository<JavaPermissionEntity, JavaPermissionId>,
        PagingAndSortingRepository<JavaPermissionEntity, JavaPermissionId> {

}
