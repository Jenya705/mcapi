package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class GetPlayerLocationRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetPlayerLocationRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER_LOCATION);
    }

    @Override
    public void handle(Request request, Response response) {
        Player player = request
                .paramOrException("id", Player.class);
        request
                .bot()
                .needPermission(Permissions.PLAYER_GET_LOCATION);
        response.ok(player.getLocation());
    }
}
