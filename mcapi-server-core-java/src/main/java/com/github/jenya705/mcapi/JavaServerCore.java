package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 *
 * Interface which methods used to link java server core and server application
 *
 * @since 1.0
 * @author Jenya705
 */
public interface JavaServerCore extends ApiServerCore {

    @Override
    JavaPlayer getPlayer(String name);

    @Override
    JavaPlayer getPlayer(UUID uniqueId);
}
