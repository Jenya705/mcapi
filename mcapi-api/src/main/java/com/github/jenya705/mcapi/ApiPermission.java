package com.github.jenya705.mcapi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jenya705
 */
public interface ApiPermission {

    @NotNull String getName();

    @Nullable String getTarget();

    @NotNull String getToken();

    boolean isEnabled();

}
