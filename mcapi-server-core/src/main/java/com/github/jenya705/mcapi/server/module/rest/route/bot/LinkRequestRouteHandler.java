package com.github.jenya705.mcapi.server.module.rest.route.bot;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ReactorUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class LinkRequestRouteHandler extends AbstractRouteHandler {

    private final LinkingModule linkingModule;

    @Inject
    public LinkRequestRouteHandler(ServerApplication application, LinkingModule linkingModule) {
        super(application, Routes.LINK_REQUEST);
        this.linkingModule = linkingModule;
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        String playerId = request.paramOrException("id");
        return core()
                .getPlayerById(playerId)
                .switchIfEmpty(Mono.error(() -> PlayerNotFoundException.create(playerId)))
                .flatMap(bot.mapUuidHolderPermission(Permissions.LINK_REQUEST))
                .doOnNext(player -> linkingModule.requestLink(
                        bot, player, request.bodyOrException(LinkRequest.class)))
                .map(player -> Response.create().noContent());
    }
}
