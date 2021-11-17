package com.github.jenya705.mcapi.module.rest.route.player;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.message.MessageUtils;
import com.github.jenya705.mcapi.module.message.TypedMessage;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.util.Selector;

/**
 * @author Jenya705
 */
public class KickPlayerRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    public KickPlayerRouteHandler() {
        super(Routes.KICK_PLAYER_SELECTOR);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        Selector<Player> players = selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                );
        bot.needPermission(Permissions.PLAYER_KICK, players);
        TypedMessage message = request
                .bodyOrException(TypedMessage.class);
        players.forEach(player ->
                MessageUtils.kick(player, message)
        );
        response.noContent();
    }
}
