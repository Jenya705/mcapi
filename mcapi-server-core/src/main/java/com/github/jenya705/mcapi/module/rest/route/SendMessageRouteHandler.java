package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.message.Message;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
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
        Selector<Player> players = selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                );
        bot.needPermission(Permissions.USER_SEND_MESSAGE, players);
        Message message = request
                .bodyOrException(Message.class);
        players.forEach(message::send);
        response.noContent();
    }
}
