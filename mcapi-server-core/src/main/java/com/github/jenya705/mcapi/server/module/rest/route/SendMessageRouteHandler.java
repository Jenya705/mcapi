package com.github.jenya705.mcapi.server.module.rest.route;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.message.Message;
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
public class SendMessageRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public SendMessageRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.SEND_MESSAGE);
        this.selectorProvider = selectorProvider;
    }

    @Override
    public Mono<Response> handle(Request request) {
        String selectorValue = request.paramOrException("selector");
        Message message = request
                .bodyOrException(Message.class);
        AbstractBot bot = request.bot();
        return selectorProvider
                .players(selectorValue, bot)
                .flatMap(Selector::errorIfEmpty)
                .flatMap(bot.mapSelectorPermission(Permissions.PLAYER_SEND_MESSAGE))
                .doOnNext(players -> players.all().forEach(message::send))
                .map(players -> Response.create().noContent());
    }
}
