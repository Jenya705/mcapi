package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.ApiPlayer;

import java.util.UUID;

/**
 *
 * An api player getter represents rest methods to get player
 *
 * @since 1.0
 * @author Jenya705
 */
public interface ApiPlayerGetter {

    ApiPlayer getPlayer(String name, String token);

    ApiPlayer getPlayer(UUID uniqueId, String token);

}
