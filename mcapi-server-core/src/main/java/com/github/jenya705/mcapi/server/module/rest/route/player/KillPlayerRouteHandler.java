package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.Selector;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class KillPlayerRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public KillPlayerRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.KILL_PLAYER_SELECTOR);
        this.selectorProvider = selectorProvider;
    }

    @Override
    public Mono<Response> handle(Request request) {
        String selectorValue = request.paramOrException("selector");
        AbstractBot bot = request.bot();
        return selectorProvider
                .players(selectorValue, bot)
                .flatMap(Selector::errorIfEmpty)
                .flatMap(bot.mapSelectorPermission(Permissions.PLAYER_KILL))
                .doOnNext(players -> players.all().forEach(Player::kill))
                .map(players -> Response.create().noContent());
    }
}
