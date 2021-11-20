package com.github.jenya705.mcapi.module.rest.route.player;

import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.EntityPermission;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
public class PlayerPermissionRouteHandler extends AbstractRouteHandler {

    public PlayerPermissionRouteHandler() {
        super(Routes.PLAYER_PERMISSION);
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
