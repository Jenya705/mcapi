package com.github.jenya705.mcapi.module.rest.route.player;

import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.util.ItemUtils;

import java.util.Optional;

/**
 * @author Jenya705
 */
public class GetPlayerInventoryItemRouteHandler extends AbstractRouteHandler {

    public GetPlayerInventoryItemRouteHandler() {
        super(Routes.PLAYER_INVENTORY_ITEM);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        Player player = request
                .paramOrException("id", Player.class);
        int item = request.paramOrException("item", int.class);
        request
                .bot()
                .needPermission(Permissions.PLAYER_ITEM_GET, player);
        response.ok(
                Optional
                        .ofNullable(player.getInventory().getItem(item))
                        .orElse(ItemUtils.empty())
        );
    }
}
