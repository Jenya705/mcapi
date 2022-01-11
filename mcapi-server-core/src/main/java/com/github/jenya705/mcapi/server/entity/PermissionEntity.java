package com.github.jenya705.mcapi.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
@Builder
@AllArgsConstructor
public class PermissionEntity {

    private final String permission;
    private final boolean global;
    private final boolean enabled;
}
