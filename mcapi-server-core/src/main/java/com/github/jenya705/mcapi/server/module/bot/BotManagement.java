package com.github.jenya705.mcapi.server.module.bot;

import com.google.inject.ImplementedBy;

import java.util.UUID;

/**
 * @author Jenya705
 */
@ImplementedBy(BotManagementImpl.class)
public interface BotManagement {

    boolean addBot(String name, UUID owner, String token);

}
