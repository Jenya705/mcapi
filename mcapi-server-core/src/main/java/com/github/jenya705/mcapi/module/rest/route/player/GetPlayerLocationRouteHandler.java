package com.github.jenya705.mcapi.module.rest.route.player;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
public class GetPlayerLocationRouteHandler extends AbstractRouteHandler {

    public GetPlayerLocationRouteHandler() {
        super(Routes.PLAYER_LOCATION);
    }

    @Override
    public void handle(Request request, Response response) {
        Player player = request
                .paramOrException("id", Player.class);
        request
                .bot()
                .needPermission(Permissions.USER_GET_LOCATION);
        response.ok(player.getLocation());
    }
}
