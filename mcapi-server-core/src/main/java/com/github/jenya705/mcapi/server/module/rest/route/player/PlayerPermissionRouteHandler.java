package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.EntityPermission;
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
public class PlayerPermissionRouteHandler extends AbstractRouteHandler {

    @Inject
    public PlayerPermissionRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER_PERMISSION);
    }

    @Override
    public void handle(Request request, Response response) {
        Player player = request
                .paramOrException("id", Player.class);
        String permissionName = request
                .paramOrException("permission");
        request
                .bot()
                .needPermission(Permissions.PLAYER_HAS_PERMISSION, player);
        response.ok(new EntityPermission(
                player.hasPermission(permissionName),
                permissionName,
                player.getUuid()
        ));
    }
}
