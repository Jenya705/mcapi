package com.github.jenya705.mcapi.token;

import java.util.UUID;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerTokenHolder {

    UUID getPlayer();

    String getToken();

    String getName();

}
