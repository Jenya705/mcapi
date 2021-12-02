package com.github.jenya705.mcapi.module.rest.route.offline;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OfflinePlayer;
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
