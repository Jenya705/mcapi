package com.github.jenya705.mcapi.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public class GetPlayerInventoryRouteHandler extends AbstractRouteHandler {

    public GetPlayerInventoryRouteHandler() {
        super(Routes.PLAYER_INVENTORY);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        Player player = request
                .paramOrException("id", Player.class);
        request
                .bot()
                .needPermission(Permissions.PLAYER_INVENTORY_GET, player);
        response.ok(player.getInventory());
    }
}
