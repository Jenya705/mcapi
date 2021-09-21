package com.github.jenya705.mcapi.module.rest;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.BodyIsEmptyException;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.util.ReactiveUtils;
import com.github.jenya705.mcapi.util.Selector;

/**
 * @author Jenya705
 */
public class SendMessageRouteHandler implements RouteHandler, BaseCommon {

    @Bean
    private SelectorProvider selectorProvider;

    @OnStartup
    public void start() {
        bean(WebServer.class).addHandler(Routes.SEND_MESSAGE, this);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request
                .header("Authorization", AbstractBot.class)
                .orElseThrow(ReactiveUtils::unknownException);
        Selector<ApiPlayer> players = selectorProvider
                .players(
                        request
                                .param("selector")
                                .orElseThrow(ReactiveUtils::unknownException),
                        bot
                );
        bot.needPermission("user.send_message", players);
        String message = request
                .body()
                .orElseThrow(BodyIsEmptyException::new);
        players.forEach(player -> player.sendMessage(message));
        response.noContent();
    }
}
