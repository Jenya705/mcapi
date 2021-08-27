package com.github.jenya705.mcapi.module.authorization;

import com.github.jenya705.mcapi.entity.AbstractBot;

/**
 * @author Jenya705
 */
public interface AuthorizationModule {

    AbstractBot bot(String authorization);

    AbstractBot rawBot(String token);
}
