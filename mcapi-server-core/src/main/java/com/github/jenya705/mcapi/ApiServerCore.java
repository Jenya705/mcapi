package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 *
 * Interface which methods used to link server core and server application
 *
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerCore {

    ApiPlayer getPlayer(String name);

    ApiPlayer getPlayer(UUID uniqueId);

    ApiServerConfiguration getConfig();

}
