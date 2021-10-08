package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface Permission {

    boolean isToggled();

    String getName();

    UUID getTarget();

}
