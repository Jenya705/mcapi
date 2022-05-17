package com.github.jenya705.mcapi.server.module.rest.route.inventory;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.inventory.InventoryViewModel;
import com.github.jenya705.mcapi.server.module.menu.MenuModule;
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
    public Mono<Response> handle(Request request) {
        String selectorValue = request.paramOrException("selector");
        AbstractBot bot = request.bot();
        return selectorProvider
                .players(selectorValue, bot)
                .flatMap(Selector::errorIfEmpty)
                .flatMap(bot.mapSelectorPermission(Permissions.PLAYER_OPEN_INVENTORY))
                .flatMap(players -> request
                        .bodyOrException(InventoryViewModel.class)
                        .createMenuView(core())
                        .map(inventoryMenuView -> {
                            menuModule.makeMenuItems(inventoryMenuView, bot.getEntity());
                            players.all().forEach(it -> it.openInventory((InventoryView) inventoryMenuView));
                            return Response.create().noContent();
                        })
                );
    }
}
