package com.github.jenya705.mcapi.server.module.rest.route.bot;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.SelectorEmptyException;
import com.github.jenya705.mcapi.rest.player.RestPlayerList;
import com.github.jenya705.mcapi.server.Bean;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.Selector;

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
                .orElseThrow(SelectorEmptyException::create);
        response.ok(new RestPlayerList(
                selectorBot
                        .getLinks()
                        .stream()
                        .map(BotLinkEntity::getTarget)
                        .toArray(UUID[]::new)
        ));
    }
}
