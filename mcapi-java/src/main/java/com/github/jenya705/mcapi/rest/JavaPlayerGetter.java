package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.JavaPlayer;

import java.util.UUID;

/**
 *
 * A java player getter represents methods to get java player
 *
 * @since 1.0
 * @author Jenya705
 */
public interface JavaPlayerGetter extends ApiPlayerGetter {

    @Override
    JavaPlayer getPlayer(String name);

    @Override
    JavaPlayer getPlayer(UUID uniqueId);
}
