package com.github.jenya705.mcapi.server.module.rest.route.inventory;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.Selector;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class CloseInventoryRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public CloseInventoryRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.CLOSE_INVENTORY);
        this.selectorProvider = selectorProvider;
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        return selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                )
                .flatMap(bot.mapSelectorPermission(Permissions.PLAYER_CLOSE_INVENTORY))
                .map(players -> {
                    players.all().forEach(Player::closeInventory);
                    return Response.create().noContent();
                });
    }
}
