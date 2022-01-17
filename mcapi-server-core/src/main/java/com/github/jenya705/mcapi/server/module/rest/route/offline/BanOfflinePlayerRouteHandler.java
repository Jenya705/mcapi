package com.github.jenya705.mcapi.server.module.rest.route.offline;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.Bean;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.message.MessageUtils;
import com.github.jenya705.mcapi.server.module.message.TypedMessage;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.Selector;

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
        Selector<OfflinePlayer> offlinePlayers =
                selectorProvider
                        .offlinePlayers(
                                request.paramOrException("selector"),
                                bot
                        );
        bot.needPermission(Permissions.PLAYER_BAN, offlinePlayers);
        TypedMessage message = request
                .bodyOrException(TypedMessage.class);
        offlinePlayers.forEach(player -> MessageUtils.ban(player, message));
        response.noContent();
    }
}
