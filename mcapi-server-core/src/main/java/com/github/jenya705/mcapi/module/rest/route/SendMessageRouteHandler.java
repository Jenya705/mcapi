package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.error.BodyIsEmptyException;
import com.github.jenya705.mcapi.module.message.SendMessage;
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
public class SendMessageRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    public SendMessageRouteHandler() {
        super(Routes.SEND_MESSAGE);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request
                .header("Authorization", AbstractBot.class)
                .orElseThrow(AuthorizationBadTokenException::new);
        Selector<ApiPlayer> players = selectorProvider
                .players(
                        request
                                .param("selector")
                                .orElseThrow(ReactiveUtils::unknownException),
                        bot
                );
        bot.needPermission("user.send_message", players);
        SendMessage message = request
                .body(SendMessage.class)
                .orElseThrow(BodyIsEmptyException::new);
        players.forEach(message::send);
        response.noContent();
    }
}
