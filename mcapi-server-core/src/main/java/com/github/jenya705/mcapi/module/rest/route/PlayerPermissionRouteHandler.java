package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.api.EntityPermission;
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
        ApiPlayer player = request
                .paramOrException("id", ApiPlayer.class);
        String permissionName = request
                .paramOrException("permission");
        request
                .bot()
                .needPermission(Permissions.USER_HAS_PERMISSION, player);
        response.ok(new EntityPermission(
                player.hasPermission(permissionName),
                permissionName,
                player.getUuid()
        ));
    }
}
