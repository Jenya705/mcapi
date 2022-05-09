package com.github.jenya705.mcapi.server.module.rest.route.bot;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.SelectorEmptyException;
import com.github.jenya705.mcapi.rest.player.RestPlayerList;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Singleton
public class GetBotLinkedPlayersRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public GetBotLinkedPlayersRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.BOT_LINKED);
        this.selectorProvider = selectorProvider;
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        return selectorProvider
                .bots(request.paramOrException("selector"), bot)
                .map(bots -> bots
                        .all()
                        .stream()
                        .findAny()
                        .orElseThrow(SelectorEmptyException::create)
                )
                .map(selectorBot -> new RestPlayerList(
                        selectorBot
                                .getLinks()
                                .stream()
                                .map(BotLinkEntity::getTarget)
                                .toArray(UUID[]::new)
                ))
                .map(list -> Response.create().ok(list));
    }
}
