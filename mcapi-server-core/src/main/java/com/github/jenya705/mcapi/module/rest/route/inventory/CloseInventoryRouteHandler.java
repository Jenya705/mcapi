package com.github.jenya705.mcapi.module.rest.route.inventory;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.util.Selector;

/**
 * @author Jenya705
 */
public class CloseInventoryRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    public CloseInventoryRouteHandler() {
        super(Routes.CLOSE_INVENTORY);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        AbstractBot bot = request.bot();
        Selector<Player> players = selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                );
        bot.needPermission(Permissions.PLAYER_CLOSE_INVENTORY, players);
        players.forEach(Player::closeInventory);
        response.noContent();
    }
}
