package com.github.jenya705.mcapi.selector;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface Selector {

    String asString();

    UUID getTarget();

    String getPermission();

}
