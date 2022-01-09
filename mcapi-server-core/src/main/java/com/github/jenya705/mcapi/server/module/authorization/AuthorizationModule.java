package com.github.jenya705.mcapi.server.module.authorization;

import com.github.jenya705.mcapi.server.entity.AbstractBot;

/**
 * @author Jenya705
 */
public interface AuthorizationModule {

    AbstractBot bot(String authorization);

    AbstractBot rawBot(String token);
}
