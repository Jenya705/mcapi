package com.github.jenya705.mcapi.module.rest.route.inventory;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.module.menu.MenuModule;
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
public class OpenInventoryRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    @Bean
    private MenuModule menuModule;

    public OpenInventoryRouteHandler() {
        super(Routes.OPEN_INVENTORY);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        AbstractBot bot = request.bot();
        Selector<Player> players = selectorProvider
                .players(
                        request.paramOrException("selector"),
                        bot
                );
        bot.needPermission(Permissions.PLAYER_OPEN_INVENTORY, players);
        InventoryMenuView inventoryMenuView = request
                .bodyOrException(InventoryMenuView.class);
        menuModule.makeMenuItems(inventoryMenuView, bot.getEntity());
        players.forEach(it -> it.openInventory((InventoryView) inventoryMenuView));
        response.noContent();
    }
}
