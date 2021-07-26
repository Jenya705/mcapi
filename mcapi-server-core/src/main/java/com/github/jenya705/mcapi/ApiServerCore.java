package com.github.jenya705.mcapi;

import java.util.List;
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

    List<? extends ApiPlayer> getPlayers();

    ApiServerConfiguration getConfig();

}
