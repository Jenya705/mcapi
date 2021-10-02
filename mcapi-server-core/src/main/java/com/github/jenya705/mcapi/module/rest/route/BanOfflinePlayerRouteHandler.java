package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.message.MessageUtils;
import com.github.jenya705.mcapi.module.message.TypedMessage;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.util.Selector;

/**
 * @author Jenya705
 */
public class BanOfflinePlayerRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    public BanOfflinePlayerRouteHandler() {
        super(Routes.OFFLINE_PLAYER_BAN);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        Selector<ApiOfflinePlayer> offlinePlayerSelector =
                selectorProvider
                        .offlinePlayers(
                                request.paramOrException("selector"),
                                bot
                        );
        TypedMessage message = request
                .bodyOrException(TypedMessage.class);
        offlinePlayerSelector.forEach(player -> MessageUtils.ban(player, message));
        response.noContent();
    }
}
