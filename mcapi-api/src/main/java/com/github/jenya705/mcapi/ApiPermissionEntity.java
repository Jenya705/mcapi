package com.github.jenya705.mcapi;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiPermissionEntity {

    String getName();

    String getToken();

    String getTarget();

    boolean isEnabled();

}
