package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface JavaServerCore {

    JavaPlayer getPlayer(String name);

    JavaPlayer getPlayer(UUID uniqueId);

}
