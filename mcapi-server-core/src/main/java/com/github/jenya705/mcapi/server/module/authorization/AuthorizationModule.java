package com.github.jenya705.mcapi.server.module.authorization;

import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(AuthorizationModuleImpl.class)
public interface AuthorizationModule {

    AbstractBot bot(String authorization);

    AbstractBot rawBot(String token);

    AbstractBot botById(int id);

}
