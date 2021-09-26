package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.BodyIsEmptyException;
import com.github.jenya705.mcapi.module.message.Message;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
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
        AbstractBot bot = request.bot();
        Selector<ApiPlayer> players = selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                );
        bot.needPermission("user.send_message", players);
        Message message = request
                .bodyOrException(Message.class);
        players.forEach(message::send);
        response.noContent();
    }
}
