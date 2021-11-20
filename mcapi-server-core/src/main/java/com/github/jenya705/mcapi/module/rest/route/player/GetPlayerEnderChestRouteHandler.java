package com.github.jenya705.mcapi.module.rest.route.player;

import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
public class GetPlayerEnderChestRouteHandler extends AbstractRouteHandler {

    public GetPlayerEnderChestRouteHandler() {
        super(Routes.PLAYER_ENDER_CHEST);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        Player player = request
                .paramOrException("id", Player.class);
        request
                .bot()
                .needPermission(Permissions.PLAYER_ENDER_CHEST_GET, player);
        response.ok(player.getEnderChest());
    }
}
