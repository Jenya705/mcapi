package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface LinkRequest {

    String[] getRequireRequestPermissions();

    String[] getOptionalRequestPermissions();

    String[] getMinecraftRequestCommands();
}
