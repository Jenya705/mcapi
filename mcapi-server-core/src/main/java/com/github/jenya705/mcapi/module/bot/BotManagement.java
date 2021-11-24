package com.github.jenya705.mcapi.module.bot;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface BotManagement {

    boolean addBot(String name, UUID owner, String token);

}
