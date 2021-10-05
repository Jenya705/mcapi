package com.github.jenya705.mcapi.module.rest.route.bot;

import com.github.jenya705.mcapi.ApiLinkRequest;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.Bean;
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
        ApiPlayer player = request
                .paramOrException("id", ApiPlayer.class);
        bot.needPermission(Permissions.LINK_REQUEST, player);
        ApiLinkRequest linkRequest = request
                .bodyOrException(ApiLinkRequest.class);
        linkingModule.requestLink(
                bot, player, linkRequest
        );
        response.noContent();
    }
}
