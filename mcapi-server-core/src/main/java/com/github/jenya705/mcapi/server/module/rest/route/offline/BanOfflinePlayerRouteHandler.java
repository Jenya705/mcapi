package com.github.jenya705.mcapi.server.module.rest.route.offline;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.message.MessageUtils;
import com.github.jenya705.mcapi.server.module.message.TypedMessage;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.Selector;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class BanOfflinePlayerRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public BanOfflinePlayerRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.OFFLINE_PLAYER_BAN);
        this.selectorProvider = selectorProvider;
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
