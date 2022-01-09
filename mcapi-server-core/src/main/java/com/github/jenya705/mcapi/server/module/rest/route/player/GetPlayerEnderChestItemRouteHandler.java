package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ItemUtils;

import java.util.Optional;

/**
 * @author Jenya705
 */
public class GetPlayerEnderChestItemRouteHandler extends AbstractRouteHandler {

    public GetPlayerEnderChestItemRouteHandler() {
        super(Routes.PLAYER_ENDER_CHEST_ITEM);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        Player player = request.paramOrException("id", Player.class);
        int item = request.paramOrException("item", int.class);
        request
                .bot()
                .needPermission(Permissions.PLAYER_ENDER_CHEST_ITEM_GET, player);
        response.ok(
                Optional.ofNullable(
                        player
                                .getEnderChest()
                                .getItem(item)
                ).orElse(ItemUtils.empty())
        );
    }
}
