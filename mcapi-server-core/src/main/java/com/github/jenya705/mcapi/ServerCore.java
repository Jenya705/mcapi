package com.github.jenya705.mcapi;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ServerCore {

    ApiPlayer getPlayer(String name);

    ApiPlayer getPlayer(UUID uniqueId);

}
