package com.github.jenya705.mcapi.server.module.rest.route.inventory;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.menu.MenuModule;
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
public class OpenInventoryRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;
    private final MenuModule menuModule;

    @Inject
    public OpenInventoryRouteHandler(ServerApplication application, SelectorProvider selectorProvider, MenuModule menuModule) {
        super(application, Routes.OPEN_INVENTORY);
        this.selectorProvider = selectorProvider;
        this.menuModule = menuModule;
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
