package com.github.jenya705.mcapi.module.rest.route.bot;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.link.LinkingModule;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
public class LinkRequestRouteHandler extends AbstractRouteHandler {

    @Bean
    private LinkingModule linkingModule;

    public LinkRequestRouteHandler() {
        super(Routes.LINK_REQUEST);
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
