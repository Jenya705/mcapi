package com.github.jenya705.mcapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {

    private String permission;
    private boolean global;
    private boolean enabled;
}
