package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.message.MessageUtils;
import com.github.jenya705.mcapi.module.message.TypedMessage;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.util.Selector;

/**
 * @author Jenya705
 */
public class BanPlayerRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    public BanPlayerRouteHandler() {
        super(Routes.BAN_PLAYER_SELECTOR);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        Selector<ApiPlayer> players = selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                );
        bot.needPermission(Permissions.USER_BAN, players);
        TypedMessage message = request
                .bodyOrException(TypedMessage.class);
        players.forEach(player ->
                MessageUtils.ban(player, message)
        );
        response.noContent();
    }
}
