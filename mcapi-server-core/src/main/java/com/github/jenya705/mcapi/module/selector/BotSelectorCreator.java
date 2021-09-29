package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.util.SelectorContainer;

import java.util.Collections;

/**
 * @author Jenya705
 */
public class BotSelectorCreator extends MapSelectorCreator<AbstractBot, DefaultSelectorCreatorData> {

    private final AuthorizationModule authorizationModule;

    public BotSelectorCreator(ServerApplication application) {
        authorizationModule = application.getBean(AuthorizationModule.class);
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
