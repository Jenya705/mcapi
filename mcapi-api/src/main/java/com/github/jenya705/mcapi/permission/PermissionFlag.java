package com.github.jenya705.mcapi.permission;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public enum PermissionFlag {

    TRUE,
    FALSE,
    UNDEFINED
    ;

    public static PermissionFlag of(boolean value) {
        return value ? TRUE : FALSE;
    }

    /**
     * Joins two flags where the first is priority
     *
     * @param permissionFlag Second flag
     * @return Result of join
     */
    public PermissionFlag join(PermissionFlag permissionFlag) {
        return this == UNDEFINED ? permissionFlag : this;
    }

    /**
     * Joins two flags and gets second if it needed
     *
     * @param getFlag Second flag getter
     * @return Result of join
     */
    public PermissionFlag join(Supplier<PermissionFlag> getFlag) {
        return this == UNDEFINED ? getFlag.get() : this;
    }

}
