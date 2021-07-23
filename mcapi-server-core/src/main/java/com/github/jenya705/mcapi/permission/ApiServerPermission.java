package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @since 1.0
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class ApiServerPermission implements ApiPermission {

    private final String name;
    private final boolean enabledByDefault;

}
