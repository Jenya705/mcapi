package com.github.jenya705.mcapi.link;

/**
 * @author Jenya705
 */
public interface LinkRequest {

    String[] getRequireRequestPermissions();

    String[] getOptionalRequestPermissions();

    String getReason();
}
