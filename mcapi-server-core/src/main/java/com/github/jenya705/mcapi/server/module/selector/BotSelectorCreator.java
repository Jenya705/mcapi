package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.util.SelectorContainer;

import java.util.Collections;

/**
 * @author Jenya705
 */
public class BotSelectorCreator extends MapSelectorCreator<AbstractBot, DefaultSelectorCreatorData> {

    public BotSelectorCreator(ServerApplication application) {
        AuthorizationModule authorizationModule =
                application.getBean(AuthorizationModule.class);
        this
                .direct(
                        (data, token) ->
                                new SelectorContainer<>(
                                        authorizationModule.rawBot(token),
                                        "", null
                                )
                )
                .defaultSelector(
                        "me",
                        data -> Collections.singletonList(data.getBot())
                );
    }
}
