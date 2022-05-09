package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.message.MessageUtils;
import com.github.jenya705.mcapi.server.module.message.TypedMessage;
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
public class KickPlayerRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public KickPlayerRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.KICK_PLAYER_SELECTOR);
        this.selectorProvider = selectorProvider;
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        TypedMessage message = request
                .bodyOrException(TypedMessage.class);
        return selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                )
                .flatMap(bot.mapSelectorPermission(Permissions.PLAYER_KICK))
                .doOnNext(players -> players.all().forEach(player -> MessageUtils.kick(player, message)))
                .map(players -> Response.create().noContent());
    }
}
