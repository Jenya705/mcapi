package com.github.jenya705.mcapi.server.module.rest.route.bot;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        Player player = request
                .paramOrException("id", Player.class);
        bot.needPermission(Permissions.LINK_REQUEST, player);
        LinkRequest linkRequest = request
                .bodyOrException(LinkRequest.class);
        linkingModule.requestLink(
                bot, player, linkRequest
        );
        response.noContent();
    }
}
