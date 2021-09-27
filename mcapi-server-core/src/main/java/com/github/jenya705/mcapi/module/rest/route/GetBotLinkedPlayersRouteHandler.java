package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.RestPlayerList;
import com.github.jenya705.mcapi.error.SelectorEmptyException;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.util.Selector;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class GetBotLinkedPlayersRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    public GetBotLinkedPlayersRouteHandler() {
        super(Routes.BOT_LINKED);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        Selector<AbstractBot> bots = selectorProvider
                .bots(request.paramOrException("selector"), bot);
        AbstractBot selectorBot = bots
                .stream()
                .findAny()
                .orElseThrow(SelectorEmptyException::new);
        response.ok(new RestPlayerList(
                selectorBot
                        .getLinks()
                        .stream()
                        .map(BotLinkEntity::getTarget)
                        .toArray(UUID[]::new)
        ));
    }
}
