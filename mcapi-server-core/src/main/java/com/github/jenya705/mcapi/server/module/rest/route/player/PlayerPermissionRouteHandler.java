package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.EntityPermission;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ReactorUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

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
    public Mono<Response> handle(Request request) {
        String playerId = request.paramOrException("id");
        String permissionName = request.paramOrException("permission");
        return core()
                .getPlayerById(playerId)
                .switchIfEmpty(Mono.error(() -> PlayerNotFoundException.create(playerId)))
                .flatMap(request.bot().mapUuidHolderPermission(Permissions.PLAYER_HAS_PERMISSION))
                .map(player -> new EntityPermission(
                        player.hasPermission(permissionName),
                        permissionName,
                        player.getUuid()
                ))
                .map(entityPermission -> Response.create().ok(entityPermission));
    }
}
