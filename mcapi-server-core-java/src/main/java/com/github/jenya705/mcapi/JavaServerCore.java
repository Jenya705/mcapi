package com.github.jenya705.mcapi;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface JavaServerCore {

    /**
     *
     * Returns player with name
     *
     * @param name Name of player
     * @return Player with name
     */
    @Nullable JavaPlayer getPlayer(String name);

    /**
     *
     * Returns player with uuid
     *
     * @param uuid Uuid of player
     * @return Player with uuid
     */
    @Nullable JavaPlayer getPlayer(UUID uuid);

}
