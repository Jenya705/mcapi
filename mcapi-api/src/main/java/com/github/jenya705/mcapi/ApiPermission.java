package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ApiPermission {

    boolean isToggled();

    String getName();

    UUID getTarget();

}
